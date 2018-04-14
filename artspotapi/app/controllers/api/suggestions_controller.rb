class API::SuggestionsController < API::BaseController
  include CoordinatesHelper
  include MultisetsHelper

  helper_method "current_user", "user_signed_in?"

  before_filter :authenticate_user_from_token!
  before_filter :set_distance_measure
  before_filter :get_coordinates

  def index
    @ranking = scoring
  end

  private

  def browsed_tags
    user_signed_in? ? current_user.user_likes : GuestLike.where(ip: request.remote_ip)
  end

  def extract_tags(image_set)
    image_set.map { |i| i.tags }.reduce(:+)
  end

  def scoring
    showcase_tags = extract_tags(browsed_tags.map(&:image)) || []
    nearest_places = Place.within(radius, origin: [@latitude, @longitude])

    nearest_places.inject(Hash.new(0)) do |hash, place|
      place_tags = extract_tags(place.images) || []
      score = distance(showcase_tags.map(&:name), place_tags.map(&:name))
      hash[score] = place

      hash
    end
  end

  def metric
    params[:metric] if params[:metric].present? && available_metric?(params[:metric])
  end

  def set_distance_measure
    set_metric(metric || :tanimoto_distance2)
  end
end
