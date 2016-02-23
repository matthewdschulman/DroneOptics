class AddStatus < ActiveRecord::Migration
  def change
  	create_table :statuses do |t|
      t.string :system_status
      t.string :next_order_id
      t.timestamps
    end

    create_table :orders do |t|
      t.string :order_id
      t.string :gps_coords
      t.string :description
    end

  end
end
