class GuestLike < ActiveRecord::Base
  belongs_to :image

  attr_accessible :image_id, :ip, :lat, :lng
end
