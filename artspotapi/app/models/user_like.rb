class UserLike < ActiveRecord::Base
  belongs_to :user
  belongs_to :image

  attr_accessible :lat, :lng, :image_id, :user_id
end
