class RemoveLikesFromImage < ActiveRecord::Migration
  def change
  	remove_column :images,  :likes
		remove_column :images,  :hates
  end
end
