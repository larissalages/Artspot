json.array! @users do |user|
  json.id              user.id
  json.name            user.name
  json.email           user.email
  json.radius          user.radius
  json.city            user.city
  json.address         user.address
  json.latitude        user.latitude
  json.longitude       user.longitude
  json.created_at      user.created_at.strftime("%F %T")
  json.updated_at      user.updated_at.strftime("%F %T")
  json.last_sign_in_at user.last_sign_in_at.strftime("%F %T") unless user.last_sign_in_at.nil?
  json.last_sign_in_ip user.last_sign_in_ip unless user.last_sign_in_ip.nil?
  json.place_ids       user.places.map { |p| {place_id: p.id} }
end
