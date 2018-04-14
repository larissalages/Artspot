# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20160116170528) do

  create_table "favorites", force: :cascade do |t|
    t.datetime "created_at",           null: false
    t.datetime "updated_at",           null: false
    t.integer  "user_id",    limit: 4
    t.integer  "image_id",   limit: 4
  end

  add_index "favorites", ["image_id"], name: "index_favorites_on_image_id", using: :btree
  add_index "favorites", ["user_id"], name: "index_favorites_on_user_id", using: :btree

  create_table "guest_hates", force: :cascade do |t|
    t.string   "ip",         limit: 255
    t.decimal  "lat",                    precision: 10, scale: 6
    t.decimal  "lng",                    precision: 10, scale: 6
    t.datetime "created_at",                                      null: false
    t.datetime "updated_at",                                      null: false
    t.integer  "image_id",   limit: 4
  end

  add_index "guest_hates", ["image_id"], name: "index_guest_hates_on_image_id", using: :btree
  add_index "guest_hates", ["lat", "lng"], name: "index_guest_hates_on_lat_and_lng", using: :btree

  create_table "guest_likes", force: :cascade do |t|
    t.string   "ip",         limit: 255
    t.decimal  "lat",                    precision: 10, scale: 6
    t.decimal  "lng",                    precision: 10, scale: 6
    t.datetime "created_at",                                      null: false
    t.datetime "updated_at",                                      null: false
    t.integer  "image_id",   limit: 4
  end

  add_index "guest_likes", ["image_id"], name: "index_guest_likes_on_image_id", using: :btree
  add_index "guest_likes", ["lat", "lng"], name: "index_guest_likes_on_lat_and_lng", using: :btree

  create_table "images", force: :cascade do |t|
    t.datetime "created_at",           null: false
    t.datetime "updated_at",           null: false
    t.integer  "user_id",    limit: 4
    t.integer  "upload_id",  limit: 4
  end

  add_index "images", ["upload_id"], name: "index_images_on_upload_id", using: :btree
  add_index "images", ["user_id"], name: "index_images_on_user_id", using: :btree

  create_table "images_places", id: false, force: :cascade do |t|
    t.integer "place_id", limit: 4, null: false
    t.integer "image_id", limit: 4, null: false
  end

  create_table "images_tags", id: false, force: :cascade do |t|
    t.integer "image_id", limit: 4, null: false
    t.integer "tag_id",   limit: 4, null: false
  end

  create_table "places", force: :cascade do |t|
    t.string   "name",          limit: 255
    t.string   "description",   limit: 255
    t.string   "address",       limit: 255
    t.decimal  "latitude",                  precision: 10, scale: 6
    t.decimal  "longitude",                 precision: 10, scale: 6
    t.string   "working_hours", limit: 255
    t.string   "contact",       limit: 255
    t.datetime "created_at",                                         null: false
    t.datetime "updated_at",                                         null: false
    t.integer  "user_id",       limit: 4
  end

  add_index "places", ["latitude", "longitude"], name: "index_places_on_latitude_and_longitude", using: :btree
  add_index "places", ["user_id"], name: "index_places_on_user_id", using: :btree

  create_table "profiles", force: :cascade do |t|
    t.datetime "created_at",                      null: false
    t.datetime "updated_at",                      null: false
    t.string   "avatar_file_name",    limit: 255
    t.string   "avatar_content_type", limit: 255
    t.integer  "avatar_file_size",    limit: 4
    t.datetime "avatar_updated_at"
  end

  create_table "tags", force: :cascade do |t|
    t.string   "name",       limit: 255
    t.datetime "created_at",             null: false
    t.datetime "updated_at",             null: false
  end

  add_index "tags", ["name"], name: "index_tags_on_name", unique: true, using: :btree

  create_table "uploads", force: :cascade do |t|
    t.datetime "created_at",                       null: false
    t.datetime "updated_at",                       null: false
    t.string   "picture_file_name",    limit: 255
    t.string   "picture_content_type", limit: 255
    t.integer  "picture_file_size",    limit: 4
    t.datetime "picture_updated_at"
    t.integer  "user_id",              limit: 4
    t.string   "sha1sum",              limit: 255
    t.string   "file_name_orig",       limit: 255
  end

  add_index "uploads", ["user_id"], name: "index_uploads_on_user_id", using: :btree

  create_table "user_hates", force: :cascade do |t|
    t.decimal  "lat",                  precision: 10, scale: 6
    t.decimal  "lng",                  precision: 10, scale: 6
    t.datetime "created_at",                                    null: false
    t.datetime "updated_at",                                    null: false
    t.integer  "user_id",    limit: 4
    t.integer  "image_id",   limit: 4
  end

  add_index "user_hates", ["image_id"], name: "index_user_hates_on_image_id", using: :btree
  add_index "user_hates", ["lat", "lng"], name: "index_user_hates_on_lat_and_lng", using: :btree
  add_index "user_hates", ["user_id"], name: "index_user_hates_on_user_id", using: :btree

  create_table "user_likes", force: :cascade do |t|
    t.decimal  "lat",                  precision: 10, scale: 6
    t.decimal  "lng",                  precision: 10, scale: 6
    t.datetime "created_at",                                    null: false
    t.datetime "updated_at",                                    null: false
    t.integer  "user_id",    limit: 4
    t.integer  "image_id",   limit: 4
  end

  add_index "user_likes", ["image_id"], name: "index_user_likes_on_image_id", using: :btree
  add_index "user_likes", ["lat", "lng"], name: "index_user_likes_on_lat_and_lng", using: :btree
  add_index "user_likes", ["user_id"], name: "index_user_likes_on_user_id", using: :btree

  create_table "users", force: :cascade do |t|
    t.string   "email",                  limit: 255,                          default: "", null: false
    t.string   "encrypted_password",     limit: 255,                          default: "", null: false
    t.string   "reset_password_token",   limit: 255
    t.datetime "reset_password_sent_at"
    t.datetime "remember_created_at"
    t.integer  "sign_in_count",          limit: 4,                            default: 0,  null: false
    t.datetime "current_sign_in_at"
    t.datetime "last_sign_in_at"
    t.string   "current_sign_in_ip",     limit: 255
    t.string   "last_sign_in_ip",        limit: 255
    t.datetime "created_at",                                                               null: false
    t.datetime "updated_at",                                                               null: false
    t.string   "name",                   limit: 255
    t.string   "authentication_token",   limit: 255
    t.integer  "radius",                 limit: 4
    t.string   "city",                   limit: 255
    t.string   "address",                limit: 255
    t.decimal  "latitude",                           precision: 10, scale: 6
    t.decimal  "longitude",                          precision: 10, scale: 6
  end

  add_index "users", ["authentication_token"], name: "index_users_on_authentication_token", unique: true, using: :btree
  add_index "users", ["email"], name: "index_users_on_email", unique: true, using: :btree
  add_index "users", ["reset_password_token"], name: "index_users_on_reset_password_token", unique: true, using: :btree

  add_foreign_key "favorites", "images"
  add_foreign_key "favorites", "users"
  add_foreign_key "guest_hates", "images"
  add_foreign_key "guest_likes", "images"
  add_foreign_key "images", "uploads"
  add_foreign_key "images", "users"
  add_foreign_key "places", "users"
  add_foreign_key "uploads", "users"
  add_foreign_key "user_hates", "images"
  add_foreign_key "user_hates", "users"
  add_foreign_key "user_likes", "images"
  add_foreign_key "user_likes", "users"
end
