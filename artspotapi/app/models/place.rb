class Place < ActiveRecord::Base
  belongs_to :user
  has_and_belongs_to_many :images

  attr_accessible :name, :description, :address, :latitude, :longitude, :working_hours, :contact

  acts_as_mappable default_units: :kms,
                   default_formula: :sphere,
                   lat_column_name: :latitude,
                   lng_column_name: :longitude
end
