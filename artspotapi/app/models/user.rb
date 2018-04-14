class User < ActiveRecord::Base
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable

  attr_accessible :name, :email, :password, :password_confirmation,
                  :address, :city, :latitude, :longitude, :radius

  has_many :places
  has_many :images
  has_many :uploads
  has_many :favorites
  has_many :user_likes
  has_many :user_hates

  def favorite?(image)
    favorites.where(image_id: image.id).empty? ? false : true
  end
end
