package org.mspring.mlog.web.module.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mspring.mlog.entity.Catalog;
import org.mspring.mlog.service.CatalogService;
import org.mspring.mlog.support.log.Log;
import org.mspring.mlog.support.resolver.QueryParam;
import org.mspring.mlog.utils.CatalogUtils;
import org.mspring.mlog.web.freemarker.widget.stereotype.Widget;
import org.mspring.mlog.web.query.CatalogQueryCriterion;
import org.mspring.platform.persistence.support.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Widget
@RequestMapping("user/post-categories")
public class User_CatalogWidget extends AbstractUserWidget {

	private CatalogService catalogService;

	@Autowired
	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	@RequestMapping("/list")
	@Log
	public String listCatalog(@ModelAttribute Page<Catalog> catalogPage,
			@ModelAttribute Catalog catalog, @QueryParam Map queryParams,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if (catalogPage == null) {
			catalogPage = new Page<Catalog>();
		}
		catalogPage.setPageSize(Integer.MAX_VALUE);
		catalogPage = catalogService.findCatalog(catalogPage,
				new CatalogQueryCriterion(queryParams));

		List<Catalog> result = CatalogUtils
				.getTreeList(catalogPage.getResult());
		catalogPage.setResult(result);

		model.addAttribute("catalogs",
				CatalogUtils.getTreeList(catalogService.findAllCatalog()));
		model.addAttribute("catalogPage", catalogPage);
		return view("catalog/list");
	}

}
