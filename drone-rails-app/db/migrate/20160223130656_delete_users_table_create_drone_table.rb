class DeleteUsersTableCreateDroneTable < ActiveRecord::Migration
  def change
  	drop_table :users
  	create_table :drones do |t|
      t.string :name
      t.string :status
      t.timestamps
    end
  end
end
