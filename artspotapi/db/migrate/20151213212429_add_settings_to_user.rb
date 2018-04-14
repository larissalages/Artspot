class AddSettingsToUser < ActiveRecord::Migration
  def change
    add_column :users, :radius, :integer
    add_column :users, :city, :string
    add_column :users, :address, :string
    add_column :users, :latitude, :string
    add_column :users, :longitude, :string
  end
end
