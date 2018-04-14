class API::ImagesController < API::BaseController
  helper_method :favorite?

  before_filter :authenticate_user_from_token!
  before_filter :authenticate_user!, except: [:all, :show]

  def all
    @images = Image.all
    render 'index'
  end

  def index
    @images = current_user.images
  end

  def show
    @image = Image.find(params[:id])
  end

  def create
    @image = Image.new
    @image.user_id = current_user.id
    valid_image? ? process_image : missing_params
  end

  def update
    @image = current_user.images.find(params[:id])
    valid_image_update? ? process_image : missing_params
  end

  def destroy
    @image = current_user.images.find(params[:id])
    @image.destroy ? render('show') : image_error
  end

  def favorite?(image)
    user_signed_in? ? current_user.favorite?(image) : false
  end

  private

  def missing_params
    render json: { error: 'missing or invalid parameters' }, status: :unprocessable_entity
  end

  def image_error
    render json: @image.errors, status: :unprocessable_entity
  end

  def valid_image?
    params[:upload_id].present? && params[:tags].present?
  end

  def valid_image_update?
    params[:upload_id].present? || params[:place_ids].present? || params[:tags].present?
  end

  def process_image
    unless params[:upload_id].nil?
      @image.upload = current_user.uploads.find(params[:upload_id])
    end

    unless params[:place_ids].nil?
      @image.places = []

      places = params[:place_ids].map { |p| p.fetch(:place_id, nil) }.compact
      Place.all.select { |p| places.include?(p.id.to_s) }.each { |p| @image.places << p }
    end

    unless params[:tags].nil?
      @image.tags = []

      tags = params[:tags].map { |p| p.fetch(:tag, nil) }.compact
      tags.each { |name| @image.tags << add_tag(name) }
    end

    @image.save ? render('show') : image_error
  end

  def add_tag(name)
    tag = Tag.find_by_name(name)
    tag = Tag.create(name: name) if tag.nil?
    tag
  end
end
