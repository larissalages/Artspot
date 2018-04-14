class API::PlacesController < API::BaseController
  before_filter :authenticate_user_from_token!, except: [:show, :all]
  before_filter :authenticate_user!, except: [:show, :all]

  def all
    @places = Place.all
    render 'index'
  end

  def index
    @places = current_user.places
  end

  def show
    @place = Place.find(params[:id])
  end

  def create
    @place = Place.new(params[:place])
    @place.user = current_user

    if @place.save
      render 'show'
    else
      render json: @place.errors, status: :unprocessable_entity
    end
  end

  def update
    @place = current_user.places.find(params[:id])

    if @place.update_attributes(params[:place])
      render 'show'
    else
      render json: @place.errors, status: :unprocessable_entity
    end
  end

  def destroy
    @place = current_user.places.find(params[:id])

    if @place.destroy
      render 'show'
    else
      render json: @place.errors, status: :unprocessable_entity
    end

    # head :no_content
  end
end
