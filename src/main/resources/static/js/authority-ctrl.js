/**
 * 
 */var app = angular.module("myapp", []);
app.controller("authority-ctrl", function($scope, $http, $location) {
	$scope.roles = [];
	$scope.admins = [];
	$scope.authorities = [];

	$scope.initialize = function() {
		$http.get("/admin/roles").then(resp => {
			$scope.roles = resp.data;
		})

		$http.get("/admin/checkadmin").then(resp => {
			$scope.admins = resp.data;
		})
		$http.get("/admin/authorities").then(resp => {
			$scope.authorities = resp.data;
		}).catch(error => {
			$location.path("/unauthorized");
		})
	}

	$scope.authority_of = function(acc, role) {
		if ($scope.authorities) {
			return $scope.authorities.find(ur => ur.user.userID == acc.userID && ur.role.roleID == role.roleID);
		}
	}
	$scope.authority_changed = function(acc, role) {
		var authority = $scope.authority_of(acc, role);
		if (authority) { /* đã cấp quyền -> thu hồi quyền */
			$scope.revoke_authority(authority);
		} else { /* ngược lại nếu chưa cấp quyền -> thêm mới */
			authority = { user: acc, role: role };
			$scope.grant_authority(authority);
		}
	}
	/* Thêm mới authority */
	$scope.grant_authority = function(authority) {
		$http.post(`/admin/authorities`, authority).then(resp => {
			$scope.authorities.push(resp.data)
			alert("Cấp quyền sử dụng thành công");
		}).catch(error => {
			alert("Cấp quyền sử dụng thất bại");
			console.log("Error", error);
		})
	}
	/* Xóa authority */
	$scope.revoke_authority = function(authority) {
		$http.delete(`/admin/authorities/${authority.authoritiesID}`).then(resp => {
			var index = $scope.authorities.findIndex(a => a.authoritiesID == authority.authoritiesID);
			$scope.authorities.splice(index, 1);
			alert("Thu hồi quyền sử dụng thành công");
		}).catch(error => {
			alert("Thu hồi quyền sử dụng thất bại");
			console.log("Error", error);
		})
	}
	$scope.filterUsersByRole = function() {
		// Lấy vai trò đã chọn
		var selectedRole = $scope.selectedRole;

		// Gọi API để lấy danh sách người dùng theo vai trò
		$http.get('/admin/authorities/filterByRole/' + selectedRole.roleID)
			.then(function(response) {
				// Lấy danh sách người dùng từ response.data
				var users = response.data;

				// Cập nhật danh sách người dùng hiển thị
				$scope.admins = users;
				console.log()
			})
			.catch(function(error) {
				console.error('Lỗi khi lấy danh sách người dùng:', error);
			});
	};
	$scope.initialize();
});