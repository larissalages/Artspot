class AddIndexToPlacesCooridantes < ActiveRecord::Migration
  def self.up
    add_index  :places, [:latitude, :longitude]
  end

  def self.down
    remove_index  :places, [:latitude, :longitude]
  end
end
