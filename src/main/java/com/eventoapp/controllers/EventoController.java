package com.eventoapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;
import com.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired 
	private EventoRepository er;
	
	@Autowired
	private ConvidadoRepository cr;
	
	
	@RequestMapping(value="/cadastrarEvento",method=RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
	
	@RequestMapping(value="/cadastrarEvento",method=RequestMethod.POST)
	public String form(@Valid Evento evento,BindingResult result, RedirectAttributes attributes) {		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("message","Verifique os Campos!!!");
			return "redirect:/cadastrarEvento";
		}
		er.save(evento);
		return "redirect:/cadastrarEvento";
	}
	
	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> eventos = er.findAll();
		mv.addObject("eventos", eventos);
				
		return mv;		
	}
	
//	@RequestMapping("/details")
	@RequestMapping("/{codigo}")
	public ModelAndView detailsEvento(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detailsEvento");
		mv.addObject("evento", evento);
		Iterable<Convidado> convidados = cr.findByEvento(evento);
		mv.addObject("convidados",convidados);
		return mv;
	}
	
	@RequestMapping(value="/{codigo}",method=RequestMethod.POST)
	public String detailsEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("message", "Verifique os Campos");
			return "redirect:/{codigo}";
		}
			
		Evento evento = er.findByCodigo(codigo);
		convidado.setEvento(evento);
		cr.save(convidado);		
		
		attributes.addFlashAttribute("message","Convidado salvo com sucesso");
		//Iterable<Convidado> convidados = cr.findByEvento(evento);
		//mv.addObject("convidados",convidados);
		
		return "redirect:/{codigo}";
	}
}
