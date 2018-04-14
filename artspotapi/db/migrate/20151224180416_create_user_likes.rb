class CreateUserLikes < ActiveRecord::Migration
  def change
    create_table :user_likes do |t|
      t.decimal :lat, { precision: 10, scale: 6 }
      t.decimal :lng, { precision: 10, scale: 6 }

      t.timestamps null: false
    end

    add_index  :user_likes, [:lat, :lng]
    add_reference :user_likes, :user, index: true, foreign_key: true
    add_reference :user_likes, :image, index: true, foreign_key: true
  end
end
