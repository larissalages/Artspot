class CreateImages < ActiveRecord::Migration
  def change
    create_table :images do |t|
      t.integer :likes
      t.integer :dislikes

      t.timestamps null: false
    end

    add_reference :images, :user, index: true, foreign_key: true
    add_reference :images, :upload, index: true, foreign_key: true
  end
end
