class API::UsersController < API::BaseController
  before_filter :authenticate_user_from_token!, except: [:create, :show, :index]
  before_filter :authenticate_user!, except: [:create, :show, :index]

  def index
    @users = User.all
  end

  def show
    @user = User.find(params[:id])
  end

  def profile
    @user = current_user
    render 'show'
  end

  def create
    @user = User.new(params[:user])

    if @user.save
      render 'show'
    else
      render json: @user.errors, status: :unprocessable_entity
    end
  end

  def update
    @user = current_user

    if @user.update_attributes(params[:user])
      render 'show'
    else
      render json: @user.errors, status: :unprocessable_entity
    end
  end

  def destroy
    @user = current_user

    if @user.destroy
      render 'show'
    else
      render json: @user.errors, status: :unprocessable_entity
    end

    # head :no_content
  end
end
