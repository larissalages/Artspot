class CreateGuestLikes < ActiveRecord::Migration
  def change
    create_table :guest_likes do |t|
      t.string :ip
      t.decimal :lat, { precision: 10, scale: 6 }
      t.decimal :lng, { precision: 10, scale: 6 }

      t.timestamps null: false
    end

    add_index  :guest_likes, [:lat, :lng]
    add_reference :guest_likes, :image, index: true, foreign_key: true
  end
end
