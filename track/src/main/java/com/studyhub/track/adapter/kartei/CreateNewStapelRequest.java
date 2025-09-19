package com.studyhub.track.adapter.kartei;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor
public class CreateNewStapelRequest{

	private String modulFachId;
	private String setName;
	private String beschreibung;
	private String lernIntervalle;
	private String username;

	public CreateNewStapelRequest(String setName, String modulFachId, String beschreibung, String lernIntervalle, String username) {
		this.setName = setName;
		this.modulFachId = modulFachId;
		this.beschreibung = beschreibung;
		this.lernIntervalle = lernIntervalle;
		this.username = username;
	}

	public void validateForBindingResult(BindingResult br) {
		if (invalidSetname()) {
			br.rejectValue("stapelName", "error.stapelName", "Geben Sie einen Setnamen ein.");
		}
		if (!invalidLernstufen()) {
			br.rejectValue("lernstufen", "error.lernstufen", "Die eingegeben Lernstufen haben kein gültiges Format.");
		}
	}

	public boolean invalidLernstufen() {
		String regex = "^(?:[1-9]\\d*m|[1-9]\\d*h|[1-9]\\d*d)(?:,(?:[1-9]\\d*m|[1-9]\\d*h|[1-9]\\d*d))*$";
		return Pattern.compile(regex).matcher(lernIntervalle).matches();
	}

	public boolean invalidSetname() {
		return setName == null || setName.isEmpty();
	}
}
