class API::BaseController < ActionController::Base
  # before_filter :authenticate_user!
  protect_from_forgery with: :null_session
  # prepend_before_filter :retrieve_auth_token

  private

  def retrieve_auth_token
    params[:auth_token] || request.headers["X-ArtSpot-Auth-Token"]
  end

  def authenticate_user_from_token!

    unless user_signed_in?
      token = retrieve_auth_token  

      unless token.nil?
        user = User.find_by_authentication_token(retrieve_auth_token)
        sign_in(user, store: false) unless user.nil?
      end
    end
  end
end
