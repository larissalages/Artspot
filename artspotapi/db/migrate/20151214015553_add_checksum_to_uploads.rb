class AddChecksumToUploads < ActiveRecord::Migration
  def change
    add_column :uploads, :sha1sum, :string
    add_column :uploads, :file_name_orig, :string
  end
end
