class API::FavoritesController < API::BaseController
  before_filter :authenticate_user_from_token!
  before_filter :authenticate_user!

  def index
    @images = current_user.favorites.map(&:image)
  end

  def show
    @favorite = get_favorite
    @favorite.present? ? render('show') : favorite_not_found
  end

  def create
    @favorite = Favorite.new
    @favorite.user = current_user
    @favorite.image = Image.find(params[:image_id])
    @favorite.save ? render('favorite') : favorite_errors
  end

  # def update
  #   @favorite = current_user.favorites.find(params[:id])
  #   @favorite.update_attributes(params[:favorite]) ? render('show') : favorite_errors
  # end

  def destroy
    @favorite = get_favorite
    @favorite.present? ? (@favorite.destroy ? render('favorite') : favorite_errors) : favorite_not_found
  end

  # def destroy
  #   @favorite = current_user.favorites.find(params[:id])
  #   @favorite.destroy ? render('show') : favorite_errors)
  # end

  private

  def get_favorite
    current_user.favorites.where(image_id: params[:id]).first
  end

  def favorite_not_found
    render json: { error: 'favorite not found' },  status: :unprocessable_entity
  end

  def favorite_errors
    render json: @favorite.errors, status: :unprocessable_entity
  end
end
