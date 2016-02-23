class StatusController < ApplicationController
	def get_current_status
		@latest_status = Status.last
		msg = { 
			:system_status => @latest_status.system_status, 
			:created_at => @latest_status.created_at,
			:next_order_id => @latest_status.next_order_id
		}
		render :json => msg
	end

	def show
		@latest_status = Status.last
	end

	def create
		@new_status = Status.new(
			"system_status" => params[:status][:system_status],
			"next_order_id" => params[:status][:next_order_id]
		)
	    if @new_status.save    
	    	redirect_to '/status'
	    else 
	    	redirect_to '/status'
	    end
	end

end
