package com.profitsoftware.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;
import java.io.Serializable;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.profitsoftware.vaadin.MyAppWidgetset")
public class MyUI extends UI {

  public class Todo implements Serializable {
    private boolean done;
    private String task = "";
    public boolean isDone() {
      return done;
    }
    public void setDone(boolean done) {
      this.done = done;
    }
    public String getTask() {
      return task;
    }
    public void setTask(String task) {
      this.task = task;
    }
  }

  private Container.Filter FILTER = new Container.Filter() {
    @Override
    public boolean passesFilter(Object itemId, Item item) throws UnsupportedOperationException {
      return !((Todo) itemId).isDone();
    }

    @Override
    public boolean appliesToProperty(Object propertyId) {
      return propertyId.equals("done");
    }
  };

  private BeanItemContainer<Todo> todoContainer = new BeanItemContainer(Todo.class);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
      setSizeFull();
      final VerticalLayout layout = new VerticalLayout();
      layout.setMargin(true);
      layout.setSizeFull();
      layout.setMargin(true);
      layout.setSpacing(true);
      setContent(layout);

      Grid grid = new Grid(todoContainer);
      grid.setSizeFull();
      grid.setColumnOrder("done", "task");
      grid.getColumn("done").setWidth(75);
      grid.getColumn("task").setExpandRatio(1);
      grid.setSelectionMode(Grid.SelectionMode.SINGLE);
      grid.setEditorEnabled(true);
      grid.setImmediate(true);

      Button addButton = new Button("Add");
      addButton.addClickListener(event -> todoContainer.addBean(new Todo()));

      CheckBox filterCheckBox = new CheckBox("Filter", false);
      filterCheckBox.addValueChangeListener(event -> {
          if (filterCheckBox.getValue()) {
            todoContainer.addContainerFilter(FILTER);
          } else {
            todoContainer.removeContainerFilter(FILTER);
          }
          }
      );

      layout.addComponents(addButton, filterCheckBox, grid);
      layout.setExpandRatio(grid, 1);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
