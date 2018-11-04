function Application() {
	this.run = function() {

		var LayoutVm = function() {
			var self = this;
			this.email = ko.observable(null);
			this.accountName = ko.observable(null);
			this.isLoading = ko.observable(false);

			this.canSendPasswordChangeRequest = ko.pureComputed(function() {
				var email = self.email();
				var accountName = self.accountName();

				return isNotEmptyString(email) &&
					isNotEmptyString(accountName) &&
					!self.isLoading();
			});

			this.sendPasswordChangeRequest = function() {
				var email = self.email().trim();
				var accountName = self.accountName().trim();
				var url = "/reset-password?" + "email=" + email +
					"&accountName=" + accountName;

				self.isLoading(true);
				$.get(url).done(function(resp) {
					alert("Успех! Моля проверете пощата си.");
				}).fail(function(resp) {
					var respText = resp.responseText;
					if (respText == "NoSuchAccountException") {
						alert("Не е намерен акаунт с такова име и имейл");
					} else if (respText == "EmailSendingException") {
						alert("Проблем при пращането на имейла. Моля опитайте пак или се свържете с администратор."
							+ "\n Възможно ако регистрацията е в абв е възможно адреса да е бил изтрит поради неактивност."
							+ "\n В такъв случай можете да опитате да го регистрирате отново и след това пак да опитате да получите нова парола от тук."
							+ "\n Ако емейл адреса ви не може да бъде възстановен моля пишете ни за да го сменим");
					} else if (respText == "TooFrequentRequestException") {
						alert("На скоро сте сменяли парола. Моля опитайте пак след няколко минути.");
					} else {
						alert("Възникна непредвидена грешка");
					}
				}).always(function() {
					self.isLoading(false);
				});
			};

			function isNotEmptyString(varToTest) {
				return typeof varToTest == "string" &&
					varToTest.trim().length > 0;
			}
		};

		var vm = new LayoutVm();
		ko.applyBindings(vm);
	};
}
