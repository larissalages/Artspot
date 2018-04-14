class API::ShowcaseController < API::BaseController
  include CoordinatesHelper

  helper_method :current_user, :user_signed_in?, :favorite?

  before_filter :authenticate_user_from_token!
  before_filter :get_image, except: [:index, :destroy]
  before_filter :get_coordinates, except: [:destroy]

  def index
    @image = retrieve_image
  end

  def like
    (user_signed_in? ? user_like : guest_like) unless @image.nil?

    @image = retrieve_image
    render 'index'
  end

  def hate
    (user_signed_in? ? user_hate : guest_hate) unless @image.nil?

    @image = retrieve_image
    render 'index'
  end

  def destroy
    user_signed_in? ? destroy_showcase_history : destroy_error
  end

  def favorite?
    user_signed_in? ? current_user.favorite?(@image) : false
  end

  private

  def get_favorite_record(image)
    current_user.favorites.where(image_id: image.id).first if user_signed_in?
  end

  def user_like
    UserLike.create(user_id: current_user.id, image_id: @image.id, lat: @latitude, lng: @longitude)
  end

  def user_hate
    UserHate.create(user_id: current_user.id, image_id: @image.id, lat: @latitude, lng: @longitude)
  end

  def guest_like
    GuestLike.create(ip: request.remote_ip, image_id: @image.id, lat: @latitude, lng: @longitude)
  end

  def guest_hate
    GuestHate.create(ip: request.remote_ip, image_id: @image.id, lat: @latitude, lng: @longitude)
  end

  def raffle(set)
    set[rand(0 .. (set.size - 1))]
  end

  def retrieve_image
    nearest_images = nearest_place_images
    visualized_set = visualized_images

    available_images = nearest_images.dup.delete_if { |i| visualized_set.include?(i) }
    available_images.empty? ? raffle(nearest_images) : raffle(available_images)
  end

  def nearest_place_images
    nearest_places = Place.within(radius, origin: [@latitude, @longitude])
    nearest_images = nearest_places.map(&:images).reduce(:+)

    nearest_images.nil? ? [] : nearest_images.uniq
  end

  def visualized_images
    user_signed_in? ? user_images : guest_images
  end

  def guest_images
    like_set = GuestLike.where(ip: request.remote_ip).map(&:image)
    hate_set = GuestHate.where(ip: request.remote_ip).map(&:image)
    used_set = like_set + hate_set
  end

  def user_images
    like_set = current_user.user_likes.map(&:image)
    hate_set = current_user.user_hates.map(&:image)
    used_set = like_set + hate_set + current_user.images
  end

  def destroy_showcase_history
    current_user.user_likes.map { |i| i.destroy }
    current_user.user_hates.map { |i| i.destroy }

    render json: { status: 'success' }
  end

  def destroy_error
    render json: { status: 'failure' }, status: :unprocessable_entity
  end

  # def image_not_found
  #   render json: { error: 'image not found' }, status: :unprocessable_entity
  # end

  def get_image
    begin
      @image = Image.find(params[:image_id])
    rescue
    end
  end
end
