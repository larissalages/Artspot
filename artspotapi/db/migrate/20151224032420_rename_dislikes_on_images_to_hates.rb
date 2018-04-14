class RenameDislikesOnImagesToHates < ActiveRecord::Migration
  def change
    rename_column :images, :dislikes, :hates
  end
end
