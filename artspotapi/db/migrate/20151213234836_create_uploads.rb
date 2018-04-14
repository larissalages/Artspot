class CreateUploads < ActiveRecord::Migration
  def change
    create_table :uploads do |t|
      t.timestamps null: false
    end

		add_attachment :uploads, :image
    add_reference :uploads, :user, index: true, foreign_key: true
  end
end
