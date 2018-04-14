class CreateFavorites < ActiveRecord::Migration
  def change
    create_table :favorites do |t|
      t.timestamps null: false
    end

    add_reference :favorites, :user, index: true, foreign_key: true
    add_reference :favorites, :image, index: true, foreign_key: true
  end
end
