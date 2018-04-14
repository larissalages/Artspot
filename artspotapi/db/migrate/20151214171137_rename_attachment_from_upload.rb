class RenameAttachmentFromUpload < ActiveRecord::Migration
  def change
    rename_column :uploads, :image_file_name, :picture_file_name
    rename_column :uploads, :image_file_size, :picture_file_size
    rename_column :uploads, :image_content_type, :picture_content_type
    rename_column :uploads, :image_updated_at, :picture_updated_at  
  end
end
