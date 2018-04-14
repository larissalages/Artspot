Rails.application.routes.draw do
  # The priority is based upon order of creation: first created -> highest priority.
  # See how all your routes lay out with "rake routes".

  # You can have the root of your site routed with "root"
  # root 'welcome#index'
  root :to => 'static#index'

  # Example of regular route:
  #   get 'products/:id' => 'catalog#view'

  # Example of named route that can be invoked with purchase_url(id: product.id)
  #   get 'products/:id/purchase' => 'catalog#purchase', as: :purchase

  # Example resource route (maps HTTP verbs to controller actions automatically):
  #   resources :products

  # Example resource route with options:
  #   resources :products do
  #     member do
  #       get 'short'
  #       post 'toggle'
  #     end
  #
  #     collection do
  #       get 'sold'
  #     end
  #   end

  # Example resource route with sub-resources:
  #   resources :products do
  #     resources :comments, :sales
  #     resource :seller
  #   end

  # Example resource route with more complex sub-resources:
  #   resources :products do
  #     resources :comments
  #     resources :sales do
  #       get 'recent', on: :collection
  #     end
  #   end

  # Example resource route with concerns:
  #   concern :toggleable do
  #     post 'toggle'
  #   end
  #   resources :posts, concerns: :toggleable
  #   resources :photos, concerns: :toggleable

  # Example resource route within a namespace:
  #   namespace :admin do
  #     # Directs /admin/products/* to Admin::ProductsController
  #     # (app/controllers/admin/products_controller.rb)
  #     resources :products
  #   end

  devise_for :users

  # namespace :api, path: '', constraints: { subdomain: 'api' }, defaults: { format: :json } do
  namespace :api, defaults: { format: :json } do
    # devise_for :users

    post '/users/sign_up' => 'users#create', as: :users_sign_up
    post '/users/sign_in' => 'sessions#create', as: :users_sign_in
    post '/users/sign_out' => 'sessions#destroy', as: :users_sign_out

    get '/users/profile' => 'users#profile', as: :user_profile
    put '/users/profile' => 'users#update', as: :update_user
    delete '/users/profile' => 'users#destroy', as: :delete_user

    get '/users' => 'users#index', as: :index_users
    get '/users/:id' => 'users#show', as: :show_user

    # resources :users

    get '/places/all' => 'places#all', as: :all_places

    resources :places

    get '/uploads/all' => 'uploads#all', as: :all_uploads

    resources :uploads

    get '/images/all' => 'images#all', as: :all_images

    resources :images

    resources :tags

    resources :favorites

    # resources :showcase

    get '/showcase' => 'showcase#index' , as: :showcase_index
    get '/showcase/like/:image_id' => 'showcase#like' , as: :showcase_like
    get '/showcase/hate/:image_id' => 'showcase#hate' , as: :showcase_hate
    delete '/showcase/history' => 'showcase#destroy' , as: :showcase_destroy

    get '/suggestion' => 'suggestions#index', as: :suggestion
  end
end
