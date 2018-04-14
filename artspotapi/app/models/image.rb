class Image < ActiveRecord::Base
  belongs_to :user
  belongs_to :upload
  has_and_belongs_to_many :places
  has_and_belongs_to_many :tags
  has_many :guest_likes
  has_many :guest_hates
  has_many :user_likes
  has_many :user_hates

  def likes
    (user_likes + guest_likes).count
  end

  def hates
    (user_hates + guest_hates).count
  end
end
