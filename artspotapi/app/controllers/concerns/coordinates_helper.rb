module CoordinatesHelper
  def radius
    params[:radius] || (current_user.radius if user_signed_in?) || 30
  end

  # def coordinates_error
  #   render json: { error: 'invalid coordinates' }, status: :unprocessable_entity
  # end

  # def has_coordinates?
  #   has_params_coordinates? || has_user_coordinates?
  # end

  def has_params_coordinates?
    params[:latitude].present? && params[:longitude].present?
  end

  def has_user_coordinates?
    user_signed_in? && current_user.latitude.present? && current_user.longitude.present?
  end

  def params_coordinates
    [params[:latitude], params[:longitude]] if has_params_coordinates?
  end

  def user_coordinates
    [current_user.latitude, current_user.longitude] if has_user_coordinates?
  end

  def ip_coordinates
    geo_loc = Geokit::Geocoders::IpGeocoder.geocode(request.remote_ip)
    [geo_loc.lat, geo_loc.lng] unless geo_loc.lat.nil? && geo_loc.lng.nil?
  end

  def default_coordinates
    cin_ufpe_addr = 'Av. Jorn. Aníbal Fernandes - Cidade Universitária, Recife - PE, 50740-560'
    cin_ufpe = Geokit::Geocoders::GoogleGeocoder.geocode(cin_ufpe_addr)
    [cin_ufpe.lat, cin_ufpe.lng]
  end

  def get_coordinates
    @latitude, @longitude = params_coordinates || user_coordinates || ip_coordinates || default_coordinates
  end
end
