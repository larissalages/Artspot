class API::TagsController < API::BaseController
  before_filter :authenticate_user_from_token!, except: [:index, :show]
  before_filter :authenticate_user!, except: [:index, :show]

  def index
    @tags = Tag.all
  end

  def show
    @tag = Tag.find(params[:id])
  end

  def create
    @tag = Tag.new(params[:tag])
    @tag.save  ? render('show') : tag_errors
  end

  def update
    @tag = Tag.find(params[:id])
    @tag.update_attributes(params[:tag]) ? render('show') : tag_errors
  end

  def destroy
    @tag = Tag.find(params[:id])
    @tag.destroy ? render('show') : tag_errors
  end

  private

  def tag_errors
    render json: @tag.errors, status: :unprocessable_entity
  end
end
