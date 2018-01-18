package br.com.boasalasdeatendimento.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.boasalasdeatendimento.model.DataSala;
import br.com.boasalasdeatendimento.model.Horario;

@Controller
public class HomeController {

	@RequestMapping("/")
	public static ModelAndView index() {
		
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("dataAtual", getDateTime());
		
		return modelAndView;
	}
	
	@RequestMapping("/index")
	public static ModelAndView index2() {
		
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("dataAtual", getDateTime());
		
		return modelAndView;
	}

	@RequestMapping(value = "/listahorarios", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public static ArrayList<Horario> listaHorarios(@RequestBody DataSala dataSala) {

		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		//String data = f.format(dataSala.getData());

		Horario horario = new Horario();

		ArrayList<Horario> listaTodosHorarios = getTodosHorarios();

		ArrayList<Horario> listaHorarios = new ArrayList<Horario>();
		horario = new Horario();
		horario.setHorario("16:00");
		listaHorarios.add(horario);

		horario = new Horario();
		horario.setHorario("11:00");
		listaHorarios.add(horario);

		for (int i = 0; i < listaHorarios.size(); i++) {
			for (int j = 0; j < listaTodosHorarios.size(); j++) {
				if(listaHorarios.get(i).getHorario().equals(listaTodosHorarios.get(j).getHorario())){
					listaTodosHorarios.remove(j);
				}
			}
		}

		return listaTodosHorarios;
	}

	private static ArrayList<Horario> getTodosHorarios() {
			
			ArrayList<Horario> listaHorarios = new ArrayList<Horario>();
			Horario horario = new Horario();
			
			horario = new Horario();
			horario.setHorario("08:00");
			listaHorarios.add(horario);
			
			horario = new Horario();
			horario.setHorario("09:00");
			listaHorarios.add(horario);
			
			horario = new Horario();
			horario.setHorario("10:00");
			listaHorarios.add(horario);
			
			horario = new Horario();
			horario.setHorario("11:00");
			listaHorarios.add(horario);
			
			horario = new Horario();
			horario.setHorario("12:00");
			listaHorarios.add(horario);
			
			horario = new Horario();
			horario.setHorario("13:00");
			listaHorarios.add(horario);
			
			horario = new Horario();
			horario.setHorario("14:00");
			listaHorarios.add(horario);
			
			horario = new Horario();
			horario.setHorario("15:00");
			listaHorarios.add(horario);
			
			horario = new Horario();
			horario.setHorario("16:00");
			listaHorarios.add(horario);
			
			return listaHorarios;
	}
	
	private static String getDateTime() { 
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
		Date date = new Date(); 
		return dateFormat.format(date); 
	}
}
