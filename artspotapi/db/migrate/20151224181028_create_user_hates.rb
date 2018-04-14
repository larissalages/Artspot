class CreateUserHates < ActiveRecord::Migration
  def change
    create_table :user_hates do |t|
      t.decimal :lat, { precision: 10, scale: 6 }
      t.decimal :lng, { precision: 10, scale: 6 }

      t.timestamps null: false
    end

    add_index  :user_hates, [:lat, :lng]
    add_reference :user_hates, :user, index: true, foreign_key: true
    add_reference :user_hates, :image, index: true, foreign_key: true
  end
end
