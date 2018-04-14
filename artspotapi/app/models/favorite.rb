class Favorite < ActiveRecord::Base
  belongs_to :user
  belongs_to :image

  validates :user, presence: true
  validates :image, presence: true
  validates :image, uniqueness: { scope: :user_id }
end
