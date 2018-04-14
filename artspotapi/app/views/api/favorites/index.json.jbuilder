json.array! @images.each do |image|
  json.id         image.id
  json.user_id    image.user_id
  json.upload_id  image.upload_id
  json.place_ids  image.places.map { |p| {place_id: p.id} }
  json.image_uri  image.upload.picture.url
  json.likes      image.likes
  json.hates      image.hates
  json.tags       image.tags.map { |t| {tag: t.name} }
  json.created_at image.created_at.strftime("%F %T")
  json.updated_at image.updated_at.strftime("%F %T")
end
