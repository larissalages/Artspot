# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

## TAGS ##

tags = [
  'bonecos', 'homem', 'mulher', 'peixes', 'boi',

  'colorido', 'claro', 'escuro', 'preto',

  'azuleijos', 'tapetes', 'acessorios', 'decorativo',
  'roupa', 'abstrato', 'jatoba', 'chapeu', 'reaproveitado',
  'tampas', 'plantas', 'animais', 'natureza', 'cestas',
  'musica', 'automoveis', 'bandas', 'instrumentos',

  'vime', 'fibra', 'madeira', 'argila', 'ferro',
  'plastico', 'pano', 'pet', 'natural', 'metal',

  'grande', 'medio', 'pequeno'
]

tags.each do |tag|
  if Tag.where(name: tag).first.nil?
    Tag.create(name: tag)
  end
end

tag_bonecos = Tag.where(name: 'bonecos').first
tag_homem = Tag.where(name: 'homem').first
tag_mulher = Tag.where(name: 'mulher').first
tag_peixes = Tag.where(name: 'peixes').first
tag_boi = Tag.where(name: 'boi').first

tag_colorido = Tag.where(name: 'colorido').first
tag_claro = Tag.where(name: 'claro').first
tag_escuro = Tag.where(name: 'escuro').first
tag_preto = Tag.where(name: 'preto').first

tag_azuleijos = Tag.where(name: 'azuleijos').first
tag_tapetes = Tag.where(name: 'tapetes').first
tag_acessorios = Tag.where(name: 'acessorios').first
tag_decorativo = Tag.where(name: 'decorativo').first
tag_roupa = Tag.where(name: 'roupa').first
tag_abstrato = Tag.where(name: 'abstrato').first
tag_jatoba = Tag.where(name: 'jatoba').first
tag_chapeu = Tag.where(name: 'chapeu').first
tag_reaproveitado = Tag.where(name: 'reaproveitado').first
tag_tampas = Tag.where(name: 'tampas').first
tag_plantas = Tag.where(name: 'plantas').first
tag_animais = Tag.where(name: 'animais').first
tag_natureza = Tag.where(name: 'natureza').first
tag_cestas = Tag.where(name: 'cestas').first
tag_musica = Tag.where(name: 'musica').first
tag_automoveis = Tag.where(name: 'automoveis').first
tag_bandas = Tag.where(name: 'bandas').first
tag_instrumentos = Tag.where(name: 'instrumentos').first

tag_vime = Tag.where(name: 'vime').first
tag_fibra = Tag.where(name: 'fibra').first
tag_madeira = Tag.where(name: 'madeira').first
tag_argila = Tag.where(name: 'argila').first
tag_ferro = Tag.where(name: 'ferro').first
tag_plastico = Tag.where(name: 'plastico').first
tag_pano = Tag.where(name: 'pano').first
tag_pet = Tag.where(name: 'pet').first
tag_natural = Tag.where(name: 'natural').first
tag_metal = Tag.where(name: 'metal').first
tag_palha = Tag.where(name: 'palha').first


tag_grande = Tag.where(name: 'grande').first
tag_medio = Tag.where(name: 'medio').first
tag_pequeno = Tag.where(name: 'pequeno').first

## USERS ##

gustavo = User.where(email: 'gpb3@cin.ufpe.br').first
kelvin = User.where(email: 'kbc@cin.ufpe.br').first
sergio = User.where(email: 'kelvincunha95@hotmail.com').first
anderson = User.where(email: 'aafu@cin.ufpe.br').first
heitor = User.where(email: 'hrm@cin.ufpe.br').first
danilo = User.where(email: 'dams@cin.ufpe.br').first
larissa = User.where(email: 'llo@cin.ufpe.br').first

## PLACES ##

# LOCAIS PARA TESTE MADEIRA E METAL
artesanato_mdf_data = {
  name: 'Artesanato em MDF',
  description: 'Loja de artesanatos em madeira',
  address: 'R. Dr. Joao Elisio, 208 - Mangueira, Recife-PE, 54508-230',
  latitude: -8.0771595,
  longitude: -34.9203827,
  working_hours: 'Segunda a Sexta das 8h às 18h',
  contact: '(81) 3428-1580'
}
artesanato_mdf = Place.new(artesanato_mdf_data)
artesanato_mdf.user = gustavo
raise Exception, "#{artesanato_mdf.errors.inspect}" unless artesanato_mdf.save

l_mar_data = {
  name: 'L Mar Artesanato',
  description: 'Loja de artesanatos em metal',
  address: 'R. do Bom Jesus, 500 E - Recife-PE, 50030-170',
  latitude: -8.0852166,
  longitude: -34.9366504,
  working_hours: 'Segunda a Sexta das 8h às 18h',
  contact: '(81) 3224-4815'
}
l_mar = Place.new(l_mar_data)
l_mar.user = larissa
raise Exception, "#{l_mar.errors.inspect}" unless l_mar.save

#### IMAGES MADEIRA ####
#############
## IMAGE 1 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/madeira_animais.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = gustavo
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

madeira_animais = Image.new
madeira_animais.user = gustavo
madeira_animais.upload = upload
madeira_animais.places << artesanato_mdf
madeira_animais.tags << tag_madeira
madeira_animais.tags << tag_decorativo
madeira_animais.tags << tag_jatoba
madeira_animais.tags << tag_medio
madeira_animais.tags << tag_natural

raise Exception, "#{madeira_animais.errors.inspect}" unless madeira_animais.save

#############
## IMAGE 2 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/madeira_aviao.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = gustavo
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

madeira_aviao = Image.new
madeira_aviao.user = gustavo
madeira_aviao.upload = upload
madeira_aviao.places << artesanato_mdf
madeira_aviao.tags << tag_madeira
madeira_aviao.tags << tag_decorativo
madeira_aviao.tags << tag_jatoba
madeira_aviao.tags << tag_medio
madeira_aviao.tags << tag_natural

raise Exception, "#{madeira_aviao.errors.inspect}" unless madeira_aviao.save

#############
## IMAGE 3 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/madeira_banco.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = gustavo
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

madeira_banco = Image.new
madeira_banco.user = gustavo
madeira_banco.upload = upload
madeira_banco.places << artesanato_mdf
madeira_banco.tags << tag_madeira
madeira_banco.tags << tag_decorativo
madeira_banco.tags << tag_jatoba
madeira_banco.tags << tag_medio
madeira_banco.tags << tag_natural

raise Exception, "#{madeira_banco.errors.inspect}" unless madeira_banco.save

#############
## IMAGE 4 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/madeira_caminhao.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = gustavo
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

madeira_caminhao = Image.new
madeira_caminhao.user = gustavo
madeira_caminhao.upload = upload
madeira_caminhao.places << artesanato_mdf
madeira_caminhao.tags << tag_madeira
madeira_caminhao.tags << tag_decorativo
madeira_caminhao.tags << tag_jatoba
madeira_caminhao.tags << tag_medio
madeira_caminhao.tags << tag_natural

raise Exception, "#{madeira_caminhao.errors.inspect}" unless madeira_caminhao.save

#############
## IMAGE 5 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/madeira_carrinhos.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = gustavo
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

madeira_carrinho = Image.new
madeira_carrinho.user = gustavo
madeira_carrinho.upload = upload
madeira_carrinho.places << artesanato_mdf
madeira_carrinho.tags << tag_madeira
madeira_carrinho.tags << tag_decorativo
madeira_carrinho.tags << tag_jatoba
madeira_carrinho.tags << tag_medio
madeira_carrinho.tags << tag_natural

raise Exception, "#{madeira_carrinho.errors.inspect}" unless madeira_carrinho.save

#############
## IMAGE 6 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/madeira_casa.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = gustavo
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

madeira_casa = Image.new
madeira_casa.user = gustavo
madeira_casa.upload = upload
madeira_casa.places << artesanato_mdf
madeira_casa.tags << tag_madeira
madeira_casa.tags << tag_decorativo
madeira_casa.tags << tag_jatoba
madeira_casa.tags << tag_medio
madeira_casa.tags << tag_natural

raise Exception, "#{madeira_casa.errors.inspect}" unless madeira_casa.save

#############
## IMAGE 7 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/madeira_helicoptero.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = gustavo
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

madeira_helicoptero = Image.new
madeira_helicoptero.user = gustavo
madeira_helicoptero.upload = upload
madeira_helicoptero.places << artesanato_mdf
madeira_helicoptero.tags << tag_madeira
madeira_helicoptero.tags << tag_decorativo
madeira_helicoptero.tags << tag_jatoba
madeira_helicoptero.tags << tag_medio
madeira_helicoptero.tags << tag_natural

raise Exception, "#{madeira_helicoptero.errors.inspect}" unless madeira_helicoptero.save

#############
## IMAGE 8 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/madeira_navio.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = gustavo
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

madeira_navio = Image.new
madeira_navio.user = gustavo
madeira_navio.upload = upload
madeira_navio.places << artesanato_mdf
madeira_navio.tags << tag_madeira
madeira_navio.tags << tag_decorativo
madeira_navio.tags << tag_jatoba
madeira_navio.tags << tag_medio
madeira_navio.tags << tag_natural

raise Exception, "#{madeira_navio.errors.inspect}" unless madeira_navio.save

#############
## IMAGE 9 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/madeira_simba.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = gustavo
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

madeira_simba = Image.new
madeira_simba.user = gustavo
madeira_simba.upload = upload
madeira_simba.places << artesanato_mdf
madeira_simba.tags << tag_madeira
madeira_simba.tags << tag_decorativo
madeira_simba.tags << tag_jatoba
madeira_simba.tags << tag_medio
madeira_simba.tags << tag_natural

raise Exception, "#{madeira_simba.errors.inspect}" unless madeira_simba.save

##############
## IMAGE 10 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/madeira_trator.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = gustavo
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

madeira_trator = Image.new
madeira_trator.user = gustavo
madeira_trator.upload = upload
madeira_trator.places << artesanato_mdf
madeira_trator.tags << tag_madeira
madeira_trator.tags << tag_decorativo
madeira_trator.tags << tag_jatoba
madeira_trator.tags << tag_medio
madeira_trator.tags << tag_natural

raise Exception, "#{madeira_trator.errors.inspect}" unless madeira_trator.save

##############
## IMAGE 11 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/metal_baixo.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = larissa
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

metal_baixo = Image.new
metal_baixo.user = larissa
metal_baixo.upload = upload
metal_baixo.places << l_mar
metal_baixo.tags << tag_metal
metal_baixo.tags << tag_acessorios
metal_baixo.tags << tag_instrumentos
metal_baixo.tags << tag_pequeno
metal_baixo.tags << tag_reaproveitado
metal_baixo.tags << tag_musica

raise Exception, "#{metal_baixo.errors.inspect}" unless metal_baixo.save

##############
## IMAGE 12 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/metal_banda.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = larissa
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

metal_banda = Image.new
metal_banda.user = larissa
metal_banda.upload = upload
metal_banda.places << l_mar
metal_banda.tags << tag_metal
metal_banda.tags << tag_acessorios
metal_banda.tags << tag_instrumentos
metal_banda.tags << tag_pequeno
metal_banda.tags << tag_reaproveitado
metal_banda.tags << tag_musica

raise Exception, "#{metal_banda.errors.inspect}" unless metal_banda.save

##############
## IMAGE 13 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/metal_barco.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = larissa
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

metal_barco = Image.new
metal_barco.user = larissa
metal_barco.upload = upload
metal_barco.places << l_mar
metal_barco.tags << tag_metal
metal_barco.tags << tag_acessorios
metal_barco.tags << tag_instrumentos
metal_barco.tags << tag_pequeno
metal_barco.tags << tag_reaproveitado
metal_barco.tags << tag_musica

raise Exception, "#{metal_barco.errors.inspect}" unless metal_barco.save

##############
## IMAGE 14 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/metal_bateria.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = larissa
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

metal_bateria = Image.new
metal_bateria.user = larissa
metal_bateria.upload = upload
metal_bateria.places << l_mar
metal_bateria.tags << tag_metal
metal_bateria.tags << tag_acessorios
metal_bateria.tags << tag_instrumentos
metal_bateria.tags << tag_pequeno
metal_bateria.tags << tag_reaproveitado
metal_bateria.tags << tag_musica

raise Exception, "#{metal_bateria.errors.inspect}" unless metal_bateria.save

##############
## IMAGE 15 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/metal_cachorro.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = larissa
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

metal_cachorro = Image.new
metal_cachorro.user = larissa
metal_cachorro.upload = upload
metal_cachorro.places << l_mar
metal_cachorro.tags << tag_metal
metal_cachorro.tags << tag_acessorios
metal_cachorro.tags << tag_instrumentos
metal_cachorro.tags << tag_pequeno
metal_cachorro.tags << tag_reaproveitado
metal_cachorro.tags << tag_musica

raise Exception, "#{metal_cachorro.errors.inspect}" unless metal_cachorro.save

##############
## IMAGE 16 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/metal_ciclista.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = larissa
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

metal_ciclista = Image.new
metal_ciclista.user = larissa
metal_ciclista.upload = upload
metal_ciclista.places << l_mar
metal_ciclista.tags << tag_metal
metal_ciclista.tags << tag_acessorios
metal_ciclista.tags << tag_instrumentos
metal_ciclista.tags << tag_pequeno
metal_ciclista.tags << tag_reaproveitado
metal_ciclista.tags << tag_musica

raise Exception, "#{metal_ciclista.errors.inspect}" unless metal_ciclista.save

##############
## IMAGE 17 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/metal_flauta.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = larissa
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

metal_flauta = Image.new
metal_flauta.user = larissa
metal_flauta.upload = upload
metal_flauta.places << l_mar
metal_flauta.tags << tag_metal
metal_flauta.tags << tag_acessorios
metal_flauta.tags << tag_instrumentos
metal_flauta.tags << tag_pequeno
metal_flauta.tags << tag_reaproveitado
metal_flauta.tags << tag_musica

raise Exception, "#{metal_flauta.errors.inspect}" unless metal_flauta.save

##############
## IMAGE 18 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/metal_musicos.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = larissa
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

metal_musicos = Image.new
metal_musicos.user = larissa
metal_musicos.upload = upload
metal_musicos.places << l_mar
metal_musicos.tags << tag_metal
metal_musicos.tags << tag_acessorios
metal_musicos.tags << tag_instrumentos
metal_musicos.tags << tag_pequeno
metal_musicos.tags << tag_reaproveitado
metal_musicos.tags << tag_musica

raise Exception, "#{metal_musicos.errors.inspect}" unless metal_musicos.save

##############
## IMAGE 19 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/metal_sax.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = larissa
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

metal_sax = Image.new
metal_sax.user = larissa
metal_sax.upload = upload
metal_sax.places << l_mar
metal_sax.tags << tag_metal
metal_sax.tags << tag_acessorios
metal_sax.tags << tag_instrumentos
metal_sax.tags << tag_pequeno
metal_sax.tags << tag_reaproveitado
metal_sax.tags << tag_musica

raise Exception, "#{metal_sax.errors.inspect}" unless metal_sax.save

##############
## IMAGE 20 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/metal_violonista.jpg')
content_type = 'image/jpg' # ex: 'image/png', 'image/jpg'

# --- DO NOT MODIFY ---
sha1sum = Digest::SHA1.file(file).to_s
file_name_orig = file.split('/').last
picture_file_name = "#{sha1sum[0..7]}.#{content_type.split('/').last}"
picture_content_type = content_type
picture_file_size = File.stat(file).size
picture_updated_at = Time.now

upload_data = {
  sha1sum: sha1sum,
  file_name_orig: file_name_orig
}

upload = Upload.new(upload_data)
upload.picture_file_name = picture_file_name
upload.picture_content_type = picture_content_type
upload.picture_file_size = picture_file_size
upload.picture_updated_at = picture_updated_at

FileUtils.cp(file, File.expand_path("public/images/#{picture_file_name}", Rails.root))
# --- END OF DO NOT MODIFY ---

upload.user = larissa
raise Exception, "#{upload.errors.inspect}" unless upload.save


## IMAGE ##

metal_violonista = Image.new
metal_violonista.user = larissa
metal_violonista.upload = upload
metal_violonista.places << l_mar
metal_violonista.tags << tag_metal
metal_violonista.tags << tag_acessorios
metal_violonista.tags << tag_instrumentos
metal_violonista.tags << tag_pequeno
metal_violonista.tags << tag_reaproveitado
metal_violonista.tags << tag_musica

raise Exception, "#{metal_violonista.errors.inspect}" unless metal_violonista.save


