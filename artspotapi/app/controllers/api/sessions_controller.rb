class API::SessionsController < API::BaseController
  before_filter :authenticate_user_from_token!, except: [:create]
  before_filter :authenticate_user!, except: [:create]

  def create
    required_params_exist? ? continue_signin : invalid_signin_attempt
  end

  def destroy
    token = params[:auth_token] || request.headers["X-ArtSpot-Auth-Token"]
    token.present? ? continue_signout(token) : auth_token_missing
  end

  private

  def invalid_signin_attempt
    render json: response_message('failure', 'wrong username or password'), status: :unauthorized
  end

  def auth_token_missing
    render json: response_message('failure', 'authentication token is missing'), status: :unprocessable_entity
  end

  def response_message(status = 'success', message = '')
    {
      auth_token: @user.try(:authentication_token),
      status: status,
      reason: message
    }
  end

  def required_params_exist?
    params[:session][:email].present? && params[:session][:password].present?
  end

  def valid_credentials?
    @user = User.find_for_database_authentication(email: params[:session][:email])
    @user.present? && @user.valid_password?(params[:session][:password])
  end

  def generate_auth_token
    @user.update_column(:authentication_token, Devise.friendly_token)
    sign_in(@user, store: false)

    render json: response_message
  end

  def continue_signin
    valid_credentials? ? generate_auth_token : invalid_signin_attempt
  end

  def continue_signout(token)
    current_user.update_column(:authentication_token, nil)
    sign_out(current_user)

    render json: response_message
  end
end
