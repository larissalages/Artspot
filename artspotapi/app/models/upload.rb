class Upload < ActiveRecord::Base
  include Paperclip::Glue

  belongs_to :user
  has_one :image

  attr_accessible :file_name_orig, :sha1sum

  has_attached_file :picture,
    path: "public/images/:filename",
    url: "/images/:basename.:extension",
    use_timestamp: false

  validates_attachment_content_type :picture, content_type: /\Aimage\/.*\Z/
end
