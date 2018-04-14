json.id              @place.id
json.name            @place.name
json.description     @place.description
json.address         @place.address
json.latitude        @place.latitude
json.longitude       @place.longitude
json.working_hours   @place.working_hours
json.contact         @place.contact
json.image_ids       @place.images.map { |i| {image_id: i.id} }
json.created_at      @place.created_at.strftime("%F %T")
json.updated_at      @place.updated_at.strftime("%F %T")
