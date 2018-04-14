class API::UploadsController < API::BaseController
  before_filter :authenticate_user_from_token!, except: [:all, :show]
  before_filter :authenticate_user!, except: [:all, :show]

  def all
    @uploads = Upload.all
    render 'index'
  end

  def index
    @uploads = current_user.uploads
  end

  def show
    @upload = Upload.find(params[:id])
  end

  def create
    @upload = Upload.new(file_name_orig: params[:file_name])
    @upload.user_id = current_user.id
    uploadable? ? process_upload : missing_params
  end

  def update
    @upload = current_user.uploads.find(params[:id])
    uploadable? ? proccess_upload : missing_params
  end

  def destroy
    @upload = current_user.uploads.find(params[:id])
    @upload.destroy ? render('show') : upload_error
  end

  private

  def upload_error
    render json: @upload.errors, status: :unprocessable_entity
  end

  def missing_params
    render json: { error: 'missing or invalid parameters' }, status: :unprocessable_entity
  end

  def uploadable?
    params[:file_name].present? && params[:content_type].present? && params[:content].present?
  end

  def parse_image_data
    image_bin_data = Base64.decode64(params[:content])
    @sha1sum = Digest::SHA1.hexdigest(image_bin_data)

    @temp_file = ::Tempfile.new("upload_#{@sha1sum[0..7]}.")
    @temp_file.binmode
    @temp_file.write(image_bin_data)
    @temp_file.rewind

    uploaded_file = ActionDispatch::Http::UploadedFile.new(tempfile: @temp_file)
    uploaded_file.original_filename = "#{@sha1sum[0..7]}.#{params[:content_type].split('/').last}"
    uploaded_file.content_type = params[:content_type]
    uploaded_file
  end

  def process_upload
    begin
      @upload.picture = parse_image_data
      @upload.sha1sum = @sha1sum
      @upload.save ? render('show') : upload_error
    ensure
      unlink_temp_file
    end
  end

  def unlink_temp_file
    if @temp_file
      @temp_file.close
      @temp_file.unlink
    end
  end
end
