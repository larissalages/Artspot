class CreateGuestHates < ActiveRecord::Migration
  def change
    create_table :guest_hates do |t|
      t.string :ip
      t.decimal :lat, { precision: 10, scale: 6 }
      t.decimal :lng, { precision: 10, scale: 6 }

      t.timestamps null: false
    end

    add_index  :guest_hates, [:lat, :lng]
    add_reference :guest_hates, :image, index: true, foreign_key: true
  end
end
