class CreatePlaces < ActiveRecord::Migration
  def change
    create_table :places do |t|
      t.string :name
      t.string :description
      t.string :address
      t.string :latitude
      t.string :longitude
      t.string :working_hours
      t.string :contact

      t.timestamps null: false
    end
  end
end
