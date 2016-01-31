package com.pedidovenda.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.pedidovenda.model.Usuario;
import com.pedidovenda.repository.Pedidos;
import com.pedidovenda.security.UsuarioLogado;
import com.pedidovenda.security.UsuarioSistema;

@Named
@ViewScoped
public class GraficoPedidosCriadosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private LineChartModel animatedModel1;
	private BarChartModel animatedModel2;

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd");
	private static DateFormat DATE_FORMAT2 = new SimpleDateFormat("dd/MM");

	@Inject
	private Pedidos pedidos;

	@Inject
	@UsuarioLogado
	private UsuarioSistema usuarioLogado;

	// private LineChartModel model;

	private void preRender() {
		this.animatedModel1 = new LineChartModel();
		this.animatedModel2 = new BarChartModel();
		adicionarSerie("Todos os Pedidos", null);
		adicionarSerie("Meus pedidos", usuarioLogado.getUsuario());
	}

	private void adicionarSerie(String rotulo, Usuario criadoPor) {

		Map<Date, BigDecimal> valoresPorData = this.pedidos
				.valoresTotaisPorData(15, criadoPor);

		LineChartSeries series = new LineChartSeries(rotulo);

		for (Date data : valoresPorData.keySet()) {
			String dataF = DATE_FORMAT.format(data);
			int dia;
			int mes;
			dia = dataF.indexOf("/");
			mes = dataF.lastIndexOf("/");
			System.out.println(dataF);
			System.out.println(dataF.substring(mes + 1));
			series.set(Integer.parseInt(dataF.substring(mes + 1)),
					valoresPorData.get(data));
		}

		// LineChartSeries series2 = new LineChartSeries();
		// series2.set(1, 2);
		// series2.set(2, 1);
		// series2.set(3, 3);
		// series2.set(4, 6);
		// series2.set(5, 8);
		// animatedModel1.addSeries(series2);

		animatedModel1.addSeries(series);
		animatedModel1.setTitle("Line Chart");
		animatedModel1.setAnimate(true);
		animatedModel1.setLegendPosition("se");
		Axis xAxis = animatedModel1.getAxis(AxisType.X);
		xAxis.setLabel("Dia");
		Axis yAxis = animatedModel1.getAxis(AxisType.Y);
		yAxis.setLabel("Venda");
		yAxis.setMin(0);

		ChartSeries seriesChart = new ChartSeries();
		
		for (Date data : valoresPorData.keySet()) {
			String dataF = DATE_FORMAT.format(data);
			int dia;
			int mes;
			dia = dataF.indexOf("/");
			mes = dataF.lastIndexOf("/");
			System.out.println(dataF);
			System.out.println(dataF.substring(mes + 1));
			seriesChart.set(Integer.parseInt(dataF.substring(mes + 1)),
					valoresPorData.get(data));
		}
		animatedModel2.addSeries(seriesChart);
		animatedModel2.setTitle("Grafico de barras");
		animatedModel2.setAnimate(true);
		animatedModel2.setLegendPosition("ne");
		Axis bxAxis = animatedModel2.getAxis(AxisType.X);
		bxAxis.setLabel("Dia");
		Axis byAxis = animatedModel2.getAxis(AxisType.Y);
		byAxis.setLabel("Venda");
		byAxis.setMin(0);

	}

	public BarChartModel getAnimatedModel2() {
		return animatedModel2;
	}

	public void setAnimatedModel2(BarChartModel animatedModel2) {
		this.animatedModel2 = animatedModel2;
	}

	public LineChartModel getAnimatedModel1() {
		return animatedModel1;
	}

	public void setAnimatedModel1(LineChartModel animatedModel1) {
		this.animatedModel1 = animatedModel1;
	}
}
