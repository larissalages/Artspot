json.array! @uploads do |upload|
  json.id              upload.id
  json.user_id         upload.user_id
  json.file_name       upload.picture_file_name
  json.file_name_orig  upload.file_name_orig
  json.file_size       upload.picture_file_size
  json.content_type    upload.picture_content_type
  json.sha1sum         upload.sha1sum
  json.image_uri       upload.picture.url
  json.created_at      upload.created_at
  json.updated_at      upload.picture_updated_at
end
