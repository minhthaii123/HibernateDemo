document.addEventListener('DOMContentLoaded', function() {
	// Follow/Unfollow functionality
	const followButtons = document.querySelectorAll('.follow-btn');
	followButtons.forEach(button => {
		button.addEventListener('click', function() {
			const userId = this.getAttribute('data-user-id');
			const action = this.getAttribute('data-action');
			const btn = this;

			// Prevent multiple clicks
			btn.disabled = true;
			btn.classList.add('loading');

			const contextPath = window.contextPath || '';

			const url = action === 'follow' ?
				`${contextPath}/user/follow/${userId}` :
				`${contextPath}/user/unfollow/${userId}`;

			console.log('Follow URL:', url); // Debug log
			console.log('Context Path:', contextPath); // Debug log

			fetch(url, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded',
					'X-Requested-With': 'XMLHttpRequest'
				}
			})
				.then(response => {
					console.log('Response status:', response.status);
					console.log('Response URL:', response.url);
					if (!response.ok) {
						throw new Error(`HTTP ${response.status}: ${response.statusText}`);
					}
					return response.text();
				})
				.then(result => {
					console.log('Response result:', result);
					if (result.trim() === 'success') {
						if (action === 'follow') {
							btn.textContent = 'Unfollow';
							btn.setAttribute('data-action', 'unfollow');
							btn.className = 'btn btn-secondary follow-btn';
						} else {
							btn.textContent = 'Follow';
							btn.setAttribute('data-action', 'follow');
							btn.className = 'btn btn-primary follow-btn';
						}
					} else if (result.trim() === 'error') {
						alert('Action failed. Please try again.');
					} else {
						alert('Unexpected response: ' + result);
					}
				})
				.catch(error => {
					console.error('Fetch Error:', error);
					alert('Network error occurred. Please check your connection and try again.');
				})
				.finally(() => {
					btn.disabled = false;
					btn.classList.remove('loading');
				});
		});
	});

	// Delete user functionality (Admin)
	const deleteUserButtons = document.querySelectorAll('.delete-user-btn');
	deleteUserButtons.forEach(button => {
		button.addEventListener('click', function() {
			const userId = this.getAttribute('data-user-id');
			const username = this.getAttribute('data-username');
			const contextPath = window.contextPath || '';

			if (confirm(`Are you sure you want to delete user "${username}"? This action cannot be undone.`)) {
				fetch(`${contextPath}/admin/users/delete/${userId}`, {
					method: 'POST',
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded',
					}
				})
					.then(response => response.text())
					.then(result => {
						if (result === 'success') {
							const row = document.getElementById(`user-${userId}`);
							if (row) {
								row.remove();
							}
							alert('User deleted successfully.');
						} else if (result === 'unauthorized') {
							alert('Unauthorized action.');
						} else {
							alert('Failed to delete user. Please try again.');
						}
					})
					.catch(error => {
						console.error('Error:', error);
						alert('An error occurred. Please try again.');
					});
			}
		});
	});

	// Delete post functionality (Admin)
	const deletePostButtons = document.querySelectorAll('.delete-post-btn');
	deletePostButtons.forEach(button => {
		button.addEventListener('click', function() {
			const postId = this.getAttribute('data-post-id');
			const postTitle = this.getAttribute('data-post-title');
			const contextPath = window.contextPath || '';

			if (confirm(`Are you sure you want to delete post "${postTitle}"? This action cannot be undone.`)) {
				fetch(`${contextPath}/admin/posts/delete/${postId}`, {
					method: 'POST',
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded',
					}
				})
					.then(response => response.text())
					.then(result => {
						if (result === 'success') {
							const row = document.getElementById(`post-${postId}`);
							if (row) {
								row.remove();
							}
							alert('Post deleted successfully.');
						} else if (result === 'unauthorized') {
							alert('Unauthorized action.');
						} else {
							alert('Failed to delete post. Please try again.');
						}
					})
					.catch(error => {
						console.error('Error:', error);
						alert('An error occurred. Please try again.');
					});
			}
		});
	});

	// Post status change functionality (Admin)
	const statusSelects = document.querySelectorAll('.status-select');
	statusSelects.forEach(select => {
		select.addEventListener('change', function() {
			const postId = this.getAttribute('data-post-id');
			const newStatus = this.value;
			const originalValue = this.getAttribute('data-original-value') || this.value;
			const contextPath = window.contextPath || '';

			fetch(`${contextPath}/admin/posts/status/${postId}`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded',
				},
				body: `status=${encodeURIComponent(newStatus)}`
			})
				.then(response => response.text())
				.then(result => {
					if (result === 'success') {
						this.setAttribute('data-original-value', newStatus);
						alert('Post status updated successfully.');
					} else if (result === 'unauthorized') {
						alert('Unauthorized action.');
						this.value = originalValue;
					} else {
						alert('Failed to update post status. Please try again.');
						this.value = originalValue;
					}
				})
				.catch(error => {
					console.error('Error:', error);
					alert('An error occurred. Please try again.');
					this.value = originalValue;
				});
		});

		select.setAttribute('data-original-value', select.value);
	});

	// Form validation
	const forms = document.querySelectorAll('form');
	forms.forEach(form => {
		form.addEventListener('submit', function(e) {
			const requiredFields = form.querySelectorAll('[required]');
			let isValid = true;

			requiredFields.forEach(field => {
				if (!field.value.trim()) {
					field.style.borderColor = '#e74c3c';
					isValid = false;
				} else {
					field.style.borderColor = '#ddd';
				}
			});

			if (!isValid) {
				e.preventDefault();
				alert('Please fill in all required fields.');
			}
		});
	});

	// Auto-resize textareas
	const textareas = document.querySelectorAll('textarea');
	textareas.forEach(textarea => {
		textarea.addEventListener('input', function() {
			this.style.height = 'auto';
			this.style.height = (this.scrollHeight) + 'px';
		});
	});

	// Search functionality
	const searchInput = document.getElementById('search-input');
	if (searchInput) {
		searchInput.addEventListener('input', function() {
			const searchTerm = this.value.toLowerCase();
			const items = document.querySelectorAll('.searchable-item');

			items.forEach(item => {
				const text = item.textContent.toLowerCase();
				if (text.includes(searchTerm)) {
					item.style.display = '';
				} else {
					item.style.display = 'none';
				}
			});
		});
	}
});