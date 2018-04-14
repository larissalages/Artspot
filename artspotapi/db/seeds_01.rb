# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

## TAGS ##

tag_bonecos = Tag.create(name: 'bonecos')
tag_homem = Tag.create(name: 'homem')
tag_mulher = Tag.create(name: 'mulher')
tag_peixes = Tag.create(name: 'peixes')
tag_boi = Tag.create(name: 'boi')

tag_colorido = Tag.create(name: 'colorido')
tag_claro = Tag.create(name: 'claro')
tag_escuro = Tag.create(name: 'escuro')
tag_preto = Tag.create(name: 'preto')

tag_azuleijos = Tag.create(name: 'azuleijos')
tag_tapetes = Tag.create(name: 'tapetes')
tag_acessorios = Tag.create(name: 'acessorios')
tag_decorativo = Tag.create(name: 'decorativo')
tag_roupa = Tag.create(name: 'roupa')
tag_abstrato = Tag.create(name: 'abstrato')
tag_jatoba = Tag.create(name: 'jatoba')
tag_chapeu = Tag.create(name: 'chapeu')
tag_reaproveitado = Tag.create(name: 'reaproveitado')
tag_tampas = Tag.create(name: 'tampas')
tag_plantas = Tag.create(name: 'plantas')
tag_animais = Tag.create(name: 'animais')
tag_natureza = Tag.create(name: 'natureza')
tag_cestas = Tag.create(name: 'cestas')
tag_musica = Tag.create(name: 'musica')
tag_automoveis = Tag.create(name: 'automoveis')
tag_bandas = Tag.create(name: 'bandas')
tag_instrumentos = Tag.create(name: 'instrumentos')

tag_vime = Tag.create(name: 'vime')
tag_fibra = Tag.create(name: 'fibra')
tag_madeira = Tag.create(name: 'madeira')
tag_argila = Tag.create(name: 'argila')
tag_ferro = Tag.create(name: 'ferro')
tag_plastico = Tag.create(name: 'plastico')
tag_pano = Tag.create(name: 'pano')
tag_pet = Tag.create(name: 'pet')
tag_natural = Tag.create(name: 'natural')
tag_metal = Tag.create(name: 'metal')
tag_palha = Tag.create(name: 'palha')


tag_grande = Tag.create(name: 'grande')
tag_medio = Tag.create(name: 'medio')
tag_pequeno = Tag.create(name: 'pequeno')


## USERS ##

gustavo_data = {
  name: 'Gustavo Pimentel',
  email: 'gpb3@cin.ufpe.br',
  password: '123qwe',
  password_confirmation: '123qwe',
  radius: 30,
  city: 'Recife',
  address: 'Praça Barão de Rio Branco, S/N - Carmo, Olinda - PE, 53020-220',
  latitude: -8.0171708,
  longitude: -34.8497015
}

gustavo = User.new(gustavo_data)
raise Exception, "#{gustavo.errors.inspect}" unless gustavo.save

kelvin_data = {
  name: 'Kelvin Cunha',
  email: 'kbc@cin.ufpe.br',
  password: '1234',
  password_confirmation: '1234',
  radius: 80,
  city: 'Cabo Sto Agostinho',
  address: 'R. Liborio, 31 - Pte Carvalhos, Cabo Sto Agostinho - PE, 54580-340',
  latitude: -8.2375298,
  longitude: -34.9818455
}

kelvin = User.new(kelvin_data)
raise Exception, "#{kelvin.errors.inspect}" unless kelvin.save

sergio_data = {
  name: 'Sergio Cunha',
  email: 'kelvincunha95@hotmail.com',
  password: 'aaa',
  password_confirmation: 'aaa',
  radius: 80,
  city: 'Cabo Sto Agostinho',
  address: 'R. Chico Mendes, 213 - Pte Carvalhos, Cabo Sto Agostinho - PE, 54580-632',
  latitude: -8.2466452,
  longitude: -34.9813643
}

sergio = User.new(sergio_data)
raise Exception, "#{sergio.errors.inspect}" unless sergio.save

anderson_data = {
  name: 'Anderson Urbano',
  email: 'aafu@cin.ufpe.br',
  password: '1234',
  password_confirmation: '1234',
  radius: 60,
  city: 'Recife',
  address: 'Rua tambiá, 09 - Varzea, Recife - PE',
  latitude: -8.036687,
  longitude: -34.9784089
}

anderson = User.new(anderson_data)
raise Exception, "#{anderson.errors.inspect}" unless anderson.save

heitor_data = {
  name: 'Heitor Rapela',
  email: 'hrm@cin.ufpe.br',
  password: 'RapelaH',
  password_confirmation: 'RapelaH',
  radius: 40,
  city: 'Jaboatão dos Guararapes',
  address: 'R Treze, 30 - UR11, Jaboatão dos Guararapes - PE',
  latitude: -8.1300839,
  longitude: -34.9656209
}

heitor = User.new(heitor_data)
raise Exception, "#{heitor.errors.inspect}" unless heitor.save

danilo_data = {
  name: 'Danilo Alfredo',
  email: 'dams@cin.ufpe.br',
  password: 'dams',
  password_confirmation: 'dams',
  radius: 50,
  city: 'Jaboatão dos Guararapes',
  address: 'R Treze, 30 - UR11, Jaboatão dos Guararapes - PE',
  latitude: -8.1300839,
  longitude: -34.9656209
}

danilo = User.new(danilo_data)
raise Exception, "#{danilo.errors.inspect}" unless danilo.save

larissa_data = {
  name: 'Larissa Lages',
  email: 'llo@cin.ufpe.br',
  password: 'llo1234',
  password_confirmation: 'llo1234',
  radius: 30,
  city: 'Recife',
  address: 'Avenida Recife, 104 - Santo Amaro, Recife - PE, 50110-727',
  latitude: -8.0897762,
  longitude: -34.9296939
}

larissa = User.new(larissa_data)
raise Exception, "#{larissa.errors.inspect}" unless larissa.save

## PLACES ##

feirinha_marco_zero_data = {
  name: 'Feirinha do Marco Zero',
  description: 'Famoso polo de artesanato localizado no bairro do Recife Antigo',
  address: 'Av. Alfredo Lisboa, s/n - Recife, PE, 50030-150',
  latitude: -8.0565326,
  longitude: -34.8713279,
  working_hours: 'Domingo das 14h as 19h',
  contact: 'Prefeitura do Recife'
}
marco_zero = Place.new(feirinha_marco_zero_data)
marco_zero.user = gustavo
raise Exception, "#{marco_zero.errors.inspect}" unless marco_zero.save

feirinha_boa_viagem_data = {
  name: 'Feirinha de Boa Viagem',
  description: 'Localizado na zona sul da cidade a beira mar da praia de Boa Viagem',
  address: '156, R. Barão de Souza Leão, 62 - Boa Viagem, Recife - PE, 51030-300',
  latitude: -8.1322661,
  longitude: -34.9005079,
  working_hours: 'Terça a Domingo das 18h as 22h',
  contact: 'Prefeitura do Recife'
}
feira_bv = Place.new(feirinha_boa_viagem_data)
feira_bv.user = gustavo
raise Exception, "#{feira_bv.errors.inspect}" unless feira_bv.save

casa_da_cultura_data = {
  name: 'Casa da Cultura',
  description: 'Localizado próximo à estação central de metrô do Recife',
  address: 'R. Floriano Peixoto, S/N - Santo antonio, Recife - PE, 50020-060',
  latitude: -8.0661075,
  longitude: -34.8833385,
  working_hours: 'Segunda a Sabado das 09h as 19h e Domingo das 09h as 14h',
  contact: '(81) 3224-0557'
}
casa_cultura = Place.new(casa_da_cultura_data)
casa_cultura.user = kelvin
raise Exception, "#{casa_cultura.errors.inspect}" unless casa_cultura.save

mercado_sao_jose_data = {
  name: 'Mercado São José',
  description: 'Localizado na Praça do Mercado, próximo a Basílica de Nossa Senhora da Penha',
  address: 'Praça Dom vital, S/N - São José, Recife - PE, 50020-906',
  latitude: -8.0684634,
  longitude: -34.8777025,
  working_hours: 'Segunda e Sabado das 06h as 17h e Domingo das 06h as 12h',
  contact: '(81) 3355-3359'
}
sao_jose = Place.new(mercado_sao_jose_data)
sao_jose.user = kelvin
raise Exception, "#{sao_jose.errors.inspect}" unless sao_jose.save

feira_bom_jesus_data = {
  name: 'Feira do Bom Jesus',
  description: 'Localizado no centro do Recife',
  address: 'Rua do Apolo, 199 - Bairro do Recife, Recife - PE, 50030-220',
  latitude: -8.0963667,
  longitude: -34.8862992,
  working_hours: 'Domingo 14h às 22h',
  contact: '(81) 3424-9232'
}
bom_jesus = Place.new(feira_bom_jesus_data)
bom_jesus.user = kelvin
raise Exception, "#{bom_jesus.errors.inspect}" unless bom_jesus.save

mercado_eufrasio_barbosa_data = {
  name: 'Mercado Eufrasio Barbosa',
  description: 'Situado na entrada da cidade',
  address: 'Avenida Sigismundo Gonçalves, s/n - Varadouro, Olinda-PE',
  latitude: -8.0200018,
  longitude: -34.8537239,
  working_hours: 'Segunda a Sabado das 9h às 17h30m',
  contact: 'Prefeitura de Olinda'
}
eufrasio_barbosa = Place.new(mercado_eufrasio_barbosa_data)
eufrasio_barbosa.user = anderson
raise Exception, "#{eufrasio_barbosa.errors.inspect}" unless eufrasio_barbosa.save

mercado_da_ribeira_data = {
  name: 'Mercado da Ribeira',
  description: 'Localizado proximo a Igreja Nossa senhora da Guadalupe',
  address: 'Rua Bernado Vieira de Melo, s/n - Ribeira, Olinda-PE',
  latitude: -8.009498,
  longitude: -34.8552204,
  working_hours: 'Segunda a Domingo das 9h às 18h30m',
  contact: '(81) 3493-9708'
}
mercado_ribeira = Place.new(mercado_da_ribeira_data)
mercado_ribeira.user = anderson
raise Exception, "#{mercado_ribeira.errors.inspect}" unless mercado_ribeira.save

mercado_da_se_data = {
  name: 'Mercado de artesanato da Sé',
  description: 'Localizado proximo a Casa dos Bonecos Gigantes do Alto da sé',
  address: 'Rua Bpo Coutinho, 699 - Carmo, Olinda-PE, 53120-130',
  latitude: -8.0132295,
  longitude: -34.85136,
  working_hours: 'Segunda a Domingo das 9h às 18h',
  contact: 'Prefeitura de Olinda'
}
mercado_se = Place.new(mercado_da_se_data)
mercado_se.user = anderson
raise Exception, "#{mercado_se.errors.inspect}" unless mercado_se.save

galpao_artesanato_data = {
  name: 'Galpão do Artesanato',
  description: 'Localizado na Zona Norte do Recife',
  address: 'Rua Tobias Barreto, 416 - São José, Recife-PE, 50020-700',
  latitude: -7.9107649,
  longitude: -35.0002548,
  working_hours: 'Segunda a Sexta das 9h às 18h e Sabado das 9h às 14h',
  contact: '(81) 3424-1121'
}
galpao_artesanato = Place.new(galpao_artesanato_data)
galpao_artesanato.user = anderson
raise Exception, "#{galpao_artesanato.errors.inspect}" unless galpao_artesanato.save

casa_do_artesanato_data = {
  name: 'Casa do Artesanato',
  description: 'Loja de Materiais para artesanato',
  address: 'Rua da Linha, 1097 - Alto da Bondade, Olinda-PE, 53170-285',
  latitude: -7.9107649,
  longitude: -35.0002548,
  working_hours: 'Segunda a Sexta das 8h às 18h e Sabado das 8h às 12h',
  contact: '(81) 3444-7465'
}
casa_artesanato = Place.new(casa_do_artesanato_data)
casa_artesanato.user = heitor
raise Exception, "#{casa_artesanato.errors.inspect}" unless casa_artesanato.save

tapetes_artesanais_data = {
  name: 'Tapetes Artesanais Nossa Senhora da Soledade',
  description: 'Loja de Tapetes',
  address: 'Santo Antonio, Carpina-PE, 55814-280',
  latitude: -7.8726769,
  longitude: -35.1320908,
  working_hours: 'Segunda a Sexta das 8h às 18h',
  contact: '(81) 3621-8102'
}
tapetes_artesanais = Place.new(tapetes_artesanais_data)
tapetes_artesanais.user = heitor
raise Exception, "#{tapetes_artesanais.errors.inspect}" unless tapetes_artesanais.save

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
artesanato_mdf.user = danilo
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

#############
## IMAGE 1 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/mulher_madeira.jpg')
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

mulher_madeira = Image.new
mulher_madeira.user = gustavo
mulher_madeira.upload = upload
mulher_madeira.places << marco_zero
mulher_madeira.tags << tag_mulher
mulher_madeira.tags << tag_madeira
mulher_madeira.tags << tag_abstrato
mulher_madeira.tags << tag_jatoba
mulher_madeira.tags << tag_medio

raise Exception, "#{mulher_madeira.errors.inspect}" unless mulher_madeira.save

#############
## IMAGE 2 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/madeira_trem.jpg')
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

upload.user = kelvin
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

madeira_trem = Image.new
madeira_trem.user = kelvin
madeira_trem.upload = upload
madeira_trem.places << casa_cultura
madeira_trem.tags << tag_madeira
madeira_trem.tags << tag_decorativo
madeira_trem.tags << tag_grande

raise Exception, "#{madeira_trem.errors.inspect}" unless madeira_trem.save

#############
## IMAGE 3 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/madeira_carro.jpg')
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

upload.user = kelvin
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

madeira_carro = Image.new
madeira_carro.user = kelvin
madeira_carro.upload = upload
madeira_carro.places << bom_jesus
madeira_carro.tags << tag_madeira
madeira_carro.tags << tag_decorativo
madeira_carro.tags << tag_medio

raise Exception, "#{madeira_carro.errors.inspect}" unless madeira_carro.save

#############
## IMAGE 4 ##
#############

File.join(Rails.root, 'test/fixtures/images/madeira_caminhao2.jpg')
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

upload.user = kelvin
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

madeira_caminhao = Image.new
madeira_caminhao.user = kelvin
madeira_caminhao.upload = upload
madeira_caminhao.places << sao_jose
madeira_caminhao.tags << tag_madeira
madeira_caminhao.tags << tag_decorativo
madeira_caminhao.tags << tag_medio

raise Exception, "#{madeira_caminhao.errors.inspect}" unless madeira_caminhao.save

#############
## IMAGE 5 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/madeira_moto.jpg')
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

upload.user = kelvin
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

madeira_moto = Image.new
madeira_moto.user = kelvin
madeira_moto.upload = upload
madeira_moto.places << eufrasio_barbosa
madeira_moto.tags << tag_madeira
madeira_moto.tags << tag_decorativo
madeira_moto.tags << tag_pequeno

raise Exception, "#{madeira_moto.errors.inspect}" unless madeira_moto.save

#############
## IMAGE 6 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/assento.jpg')
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

upload.user = anderson
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

assento = Image.new
assento.user = anderson
assento.upload = upload
assento.places << mercado_ribeira
assento.tags << tag_acessorios
assento.tags << tag_decorativo
assento.tags << tag_grande

raise Exception, "#{assento.errors.inspect}" unless assento.save

#############
## IMAGE 7 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/madeira_portaLapis.jpg')
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

upload.user = anderson
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

madeira_portaLapis = Image.new
madeira_portaLapis.user = anderson
madeira_portaLapis.upload = upload
madeira_portaLapis.places << mercado_ribeira
madeira_portaLapis.tags << tag_acessorios
madeira_portaLapis.tags << tag_grande
madeira_portaLapis.tags << tag_madeira

raise Exception, "#{madeira_portaLapis.errors.inspect}" unless madeira_portaLapis.save

#############
## IMAGE 8 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/bonecos_cangaco.jpg')
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

upload.user = anderson
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

bonecos_cangaco = Image.new
bonecos_cangaco.user = anderson
bonecos_cangaco.upload = upload
bonecos_cangaco.places << mercado_se
bonecos_cangaco.tags << tag_homem
bonecos_cangaco.tags << tag_decorativo
bonecos_cangaco.tags << tag_pequeno
bonecos_cangaco.tags << tag_argila

raise Exception, "#{bonecos_cangaco.errors.inspect}" unless bonecos_cangaco.save

#############
## IMAGE 9 ##
#############

file = File.join(Rails.root, 'test/fixtures/images/bonecos_boi.jpg')
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

upload.user = anderson
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

bonecos_boi = Image.new
bonecos_boi.user = anderson
bonecos_boi.upload = upload
bonecos_boi.places << mercado_se
bonecos_boi.tags << tag_homem
bonecos_boi.tags << tag_decorativo
bonecos_boi.tags << tag_pequeno
bonecos_boi.tags << tag_argila
bonecos_boi.tags << tag_boi

raise Exception, "#{bonecos_boi.errors.inspect}" unless bonecos_boi.save

##############
## IMAGE 10 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/bonecos_ferro.jpg')
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

upload.user = anderson
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

bonecos_ferro = Image.new
bonecos_ferro.user = anderson
bonecos_ferro.upload = upload
bonecos_ferro.places << galpao_artesanato
bonecos_ferro.tags << tag_decorativo
bonecos_ferro.tags << tag_pequeno
bonecos_ferro.tags << tag_ferro

raise Exception, "#{bonecos_ferro.errors.inspect}" unless bonecos_ferro.save

##############
## IMAGE 11 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/bonecas_pano.jpg')
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

upload.user = anderson
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

bonecas_pano = Image.new
bonecas_pano.user = anderson
bonecas_pano.upload = upload
bonecas_pano.places << galpao_artesanato
bonecas_pano.tags << tag_mulher
bonecas_pano.tags << tag_decorativo
bonecas_pano.tags << tag_pequeno
bonecas_pano.tags << tag_pano

raise Exception, "#{bonecas_pano.errors.inspect}" unless bonecas_pano.save

##############
## IMAGE 12 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/cesta.jpg')
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

upload.user = heitor
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

cesta = Image.new
cesta.user = heitor
cesta.upload = upload
cesta.places << casa_artesanato
cesta.tags << tag_cestas
cesta.tags << tag_fibra
cesta.tags << tag_natural
cesta.tags << tag_grande
cesta.tags << tag_acessorios

raise Exception, "#{cesta.errors.inspect}" unless cesta.save

##############
## IMAGE 13 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/cesta2.jpg')
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

upload.user = heitor
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

cesta2 = Image.new
cesta2.user = heitor
cesta2.upload = upload
cesta2.places << casa_artesanato
cesta2.tags << tag_cestas
cesta2.tags << tag_fibra
cesta2.tags << tag_natural
cesta2.tags << tag_medio
cesta2.tags << tag_acessorios

raise Exception, "#{cesta2.errors.inspect}" unless cesta2.save

##############
## IMAGE 14 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/cesta3.jpg')
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

upload.user = heitor
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

cesta3 = Image.new
cesta3.user = heitor
cesta3.upload = upload
cesta3.places << casa_artesanato
cesta3.tags << tag_cestas
cesta3.tags << tag_fibra
cesta3.tags << tag_natural
cesta3.tags << tag_medio
cesta3.tags << tag_acessorios

raise Exception, "#{cesta3.errors.inspect}" unless cesta3.save

##############
## IMAGE 15 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/cesta4.jpg')
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

upload.user = heitor
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

cesta4 = Image.new
cesta4.user = heitor
cesta4.upload = upload
cesta4.places << casa_artesanato
cesta4.tags << tag_cestas
cesta4.tags << tag_vime
cesta4.tags << tag_natural
cesta4.tags << tag_medio
cesta4.tags << tag_pequeno
cesta4.tags << tag_acessorios

raise Exception, "#{cesta4.errors.inspect}" unless cesta4.save

##############
## IMAGE 16 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/cesta5.jpg')
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

upload.user = heitor
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

cesta5 = Image.new
cesta5.user = heitor
cesta5.upload = upload
cesta5.places << casa_artesanato
cesta5.tags << tag_cestas
cesta5.tags << tag_fibra
cesta5.tags << tag_natural
cesta5.tags << tag_pequeno
cesta5.tags << tag_acessorios

raise Exception, "#{cesta5.errors.inspect}" unless cesta5.save

##############
## IMAGE 17 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/tapete1.jpg')
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

upload.user = heitor
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

tapete1 = Image.new
tapete1.user = heitor
tapete1.upload = upload
tapete1.places << tapetes_artesanais
tapete1.tags << tag_tapetes
tapete1.tags << tag_decorativo
tapete1.tags << tag_medio

raise Exception, "#{tapete1.errors.inspect}" unless tapete1.save

##############
## IMAGE 18 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/tapete2.jpg')
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

upload.user = heitor
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

tapete2 = Image.new
tapete2.user = heitor
tapete2.upload = upload
tapete2.places << tapetes_artesanais
tapete2.tags << tag_tapetes
tapete2.tags << tag_decorativo
tapete2.tags << tag_grande
tapete2.tags << tag_colorido

raise Exception, "#{tapete2.errors.inspect}" unless tapete2.save

##############
## IMAGE 19 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/tapete3.jpg')
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

upload.user = heitor
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

tapete3 = Image.new
tapete3.user = heitor
tapete3.upload = upload
tapete3.places << tapetes_artesanais
tapete3.tags << tag_tapetes
tapete3.tags << tag_decorativo
tapete3.tags << tag_pequeno
tapete3.tags << tag_acessorios
tapete3.tags << tag_peixes
tapete3.tags << tag_animais

raise Exception, "#{tapete3.errors.inspect}" unless tapete3.save

##############
## IMAGE 20 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/tapete4.jpg')
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

upload.user = heitor
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

tapete4 = Image.new
tapete4.user = heitor
tapete4.upload = upload
tapete4.places << tapetes_artesanais
tapete4.tags << tag_tapetes
tapete4.tags << tag_decorativo
tapete4.tags << tag_medio
tapete4.tags << tag_tampas
tapete4.tags << tag_pet
tapete4.tags << tag_plastico
tapete4.tags << tag_reaproveitado

raise Exception, "#{tapete4.errors.inspect}" unless tapete4.save

##############
## IMAGE 21 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/tapete5.jpg')
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

upload.user = heitor
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

tapete5 = Image.new
tapete5.user = heitor
tapete5.upload = upload
tapete5.places << tapetes_artesanais
tapete5.tags << tag_tapetes
tapete5.tags << tag_decorativo
tapete5.tags << tag_medio
tapete5.tags << tag_tampas
tapete5.tags << tag_pet
tapete5.tags << tag_plastico
tapete5.tags << tag_reaproveitado

raise Exception, "#{tapete5.errors.inspect}" unless tapete5.save

##############
## IMAGE 22 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/casas_bv.jpg')
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

casas_bv = Image.new
casas_bv.user = gustavo
casas_bv.upload = upload
casas_bv.places << feira_bv
casas_bv.tags << tag_decorativo
casas_bv.tags << tag_colorido
casas_bv.tags << tag_pequeno
casas_bv.tags << tag_madeira

raise Exception, "#{casas_bv.errors.inspect}" unless casas_bv.save

##############
## IMAGE 23 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/botoes_arvore.jpg')
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

botoes_arvore = Image.new
botoes_arvore.user = larissa
botoes_arvore.upload = upload
botoes_arvore.places << mercado_se
botoes_arvore.tags << tag_decorativo
botoes_arvore.tags << tag_colorido
botoes_arvore.tags << tag_medio
botoes_arvore.tags << tag_plastico

raise Exception, "#{botoes_arvore.errors.inspect}" unless botoes_arvore.save

##############
## IMAGE 24 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/botoes_coruja.jpg')
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

botoes_coruja = Image.new
botoes_coruja.user = larissa
botoes_coruja.upload = upload
botoes_coruja.places << mercado_se
botoes_coruja.tags << tag_decorativo
botoes_coruja.tags << tag_colorido
botoes_coruja.tags << tag_pequeno
botoes_coruja.tags << tag_plastico

raise Exception, "#{botoes_coruja.errors.inspect}" unless botoes_coruja.save

##############
## IMAGE 25 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/botoes_relogio.jpg')
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

botoes_relogio = Image.new
botoes_relogio.user = larissa
botoes_relogio.upload = upload
botoes_relogio.places << mercado_se
botoes_relogio.tags << tag_decorativo
botoes_relogio.tags << tag_colorido
botoes_relogio.tags << tag_grande
botoes_relogio.tags << tag_plastico

raise Exception, "#{botoes_relogio.errors.inspect}" unless botoes_relogio.save

##############
## IMAGE 26 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/chapeu2.jpg')
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

upload.user = danilo
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

chapeu2 = Image.new
chapeu2.user = danilo
chapeu2.upload = upload
chapeu2.places << feira_bv
chapeu2.tags << tag_acessorios
chapeu2.tags << tag_escuro
chapeu2.tags << tag_fibra
chapeu2.tags << tag_chapeu
chapeu2.tags << tag_natural

raise Exception, "#{chapeu2.errors.inspect}" unless chapeu2.save

##############
## IMAGE 27 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/chapeu3.jpg')
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

upload.user = danilo
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

chapeu3 = Image.new
chapeu3.user = danilo
chapeu3.upload = upload
chapeu3.places << feira_bv
chapeu3.tags << tag_acessorios
chapeu3.tags << tag_claro
chapeu3.tags << tag_palha
chapeu3.tags << tag_chapeu
chapeu3.tags << tag_natural
chapeu3.tags << tag_mulher

raise Exception, "#{chapeu3.errors.inspect}" unless chapeu3.save

##############
## IMAGE 28 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/chapeu_palha.jpg')
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

upload.user = danilo
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

chapeu_palha = Image.new
chapeu_palha.user = danilo
chapeu_palha.upload = upload
chapeu_palha.places << feira_bv
chapeu_palha.tags << tag_acessorios
chapeu_palha.tags << tag_escuro
chapeu_palha.tags << tag_palha
chapeu_palha.tags << tag_chapeu
chapeu_palha.tags << tag_natural
chapeu_palha.tags << tag_mulher

raise Exception, "#{chapeu_palha.errors.inspect}" unless chapeu_palha.save

##############
## IMAGE 29 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/ferro_anjo.jpg')
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

upload.user = anderson
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

ferro_anjo = Image.new
ferro_anjo.user = anderson
ferro_anjo.upload = upload
ferro_anjo.places << galpao_artesanato
ferro_anjo.tags << tag_decorativo
ferro_anjo.tags << tag_escuro
ferro_anjo.tags << tag_pequeno
ferro_anjo.tags << tag_ferro

raise Exception, "#{ferro_anjo.errors.inspect}" unless ferro_anjo.save

##############
## IMAGE 30 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/ferro_boneco.jpg')
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

upload.user = anderson
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

ferro_boneco = Image.new
ferro_boneco.user = anderson
ferro_boneco.upload = upload
ferro_boneco.places << mercado_ribeira
ferro_boneco.tags << tag_decorativo
ferro_boneco.tags << tag_escuro
ferro_boneco.tags << tag_pequeno
ferro_boneco.tags << tag_ferro

raise Exception, "#{ferro_boneco.errors.inspect}" unless ferro_boneco.save

##############
## IMAGE 31 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/ferro_cafe.jpg')
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

upload.user = anderson
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

ferro_cafe = Image.new
ferro_cafe.user = anderson
ferro_cafe.upload = upload
ferro_cafe.places << mercado_ribeira
ferro_cafe.tags << tag_decorativo
ferro_cafe.tags << tag_escuro
ferro_cafe.tags << tag_grande
ferro_cafe.tags << tag_plastico
ferro_cafe.tags << tag_ferro

raise Exception, "#{ferro_cafe.errors.inspect}" unless ferro_cafe.save

##############
## IMAGE 32 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/luminaria_coco.jpg')
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

upload.user = danilo
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

luminaria_coco = Image.new
luminaria_coco.user = danilo
luminaria_coco.upload = upload
luminaria_coco.places << bom_jesus
luminaria_coco.tags << tag_decorativo
luminaria_coco.tags << tag_escuro
luminaria_coco.tags << tag_grande
luminaria_coco.tags << tag_reaproveitado
luminaria_coco.tags << tag_natural

raise Exception, "#{luminaria_coco.errors.inspect}" unless luminaria_coco.save

##############
## IMAGE 33 ##
##############

file = File.join(Rails.root, 'test/fixtures/images/coroa_mdf.jpg')
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

upload.user = heitor
raise Exception, "#{upload.errors.inspect}" unless upload.save

## IMAGE ##

coroa_mdf = Image.new
coroa_mdf.user = heitor
coroa_mdf.upload = upload
coroa_mdf.places << casa_artesanato
coroa_mdf.tags << tag_decorativo
coroa_mdf.tags << tag_acessorios
coroa_mdf.tags << tag_madeira
coroa_mdf.tags << tag_medio

raise Exception, "#{coroa_mdf.errors.inspect}" unless coroa_mdf.save
