

class FriendHandler {

	/*
	*  Displays the search friend pages
	*
	*  GET /user-search-friend
	*/
	static Object showSearchFriend(Context context) {
		if (context.isLoggedIn()) {
			return context.render("/WEB-INF/friend-search.jsp");
		}
		return context.redirect("/user-check-email");
	}	

}
