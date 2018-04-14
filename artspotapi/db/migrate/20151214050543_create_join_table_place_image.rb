class CreateJoinTablePlaceImage < ActiveRecord::Migration
  def change
    create_join_table :places, :images do |t|
      # t.index [:place_id, :image_id]
      # t.index [:image_id, :place_id]
    end
  end
end
