package pl.karol202.ev3controller.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.adapter.AdapterExpressionsTypesList;
import pl.karol202.ev3controller.expression.ExpressionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class DialogExpressionType
{
	public interface ExpressionTypeListener
	{
		void onExpressionTypeSelected(ExpressionType type);
	}

	private Dialog dialog;
	private ExpressionTypeListener typeListener;
	private ArrayList<ExpressionType> typesList;

	public DialogExpressionType(Activity activity, ExpressionTypeListener listener, int[] filter)
	{
		this.dialog = new Dialog(activity);
		this.typeListener = listener;
		this.typesList = new ArrayList<ExpressionType>(Arrays.asList(ExpressionType.values()));

		filterTypes(typesList, filter);
		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.dialog_expressions_types, null);
		dialog.setTitle(R.string.title_dialog_expressions_types);
		dialog.setContentView(view);
		ArrayAdapter<ExpressionType> listAdapter = new AdapterExpressionsTypesList(activity, typesList);
		ListView listViewTypes = (ListView) view.findViewById(R.id.listView_expression_type);
		listViewTypes.setAdapter(listAdapter);
		listViewTypes.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
			{
				typeListener.onExpressionTypeSelected(typesList.get(position));
				dialog.cancel();
			}
		});
	}

	public void show()
	{
		dialog.show();
	}

	private void filterTypes(ArrayList<ExpressionType> expressions, int[] filter)
	{
		Iterator<ExpressionType> iterator = expressions.iterator();
		while(iterator.hasNext())
		{
			ExpressionType type = iterator.next();
			boolean found = false;
			for(int i : filter)
			{
				if(type.getValueType().ordinal() == i)
				{
					found = true;
					break;
				}
			}
			if(!found) iterator.remove();
		}
	}
}
