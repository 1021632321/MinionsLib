package lib.minions.com.minionslib.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.SpannableString;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.effects.BaseEffects;

import java.util.ArrayList;
import java.util.List;

import lib.minions.com.minionslib.R;


public class ButtomDialog {
	private Context context;
	private Dialog dialog;
	private TextView txt_title;
	private TextView txt_cancel;
	private LinearLayout lLayout_content;
	private ScrollView sLayout_content;
	private boolean showTitle = false;
	private List<SheetItem> sheetItemList;
	private Display display;
	private Effectstype effectstype;

	public ButtomDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
		effectstype = Effectstype.RotateBottom;
	}

	/**
	 *设置底部对话框
	 *@author chen
	 *created at 2016/6/1 11:03
	 */
	public Dialog creatButtomDialog(final View view){
		Dialog dialog = new Dialog(context, R.style.MyDialogStyle);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
		dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = ScreenUtil.getScreenHeight(context)/10 * 7;
		dialogWindow.setAttributes(lp);
		dialog.setContentView(view );
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				if(null != effectstype){
					BaseEffects animator = effectstype.getAnimator();
					animator.setDuration(300);
					animator.start(view);
				}
			}
		});
		return dialog;
	}

	/**
	 *设置底部对话框
	 *@author chen
	 *created at 2016/6/1 11:03
	 */
	public Dialog creatButtomDialog(final View view, final Effectstype effectstype){
		Dialog dialog = new Dialog(context, R.style.MyDialogStyle);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
		dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = ScreenUtil.getScreenHeight(context)/10 * 7;
		dialogWindow.setAttributes(lp);
		dialog.setContentView(view );
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				if(null != effectstype){
					BaseEffects animator = effectstype.getAnimator();
					animator.setDuration(300);
					animator.start(view);
				}
			}
		});
		return dialog;
	}

	public ButtomDialog setEffect(Effectstype effect){
		this.effectstype = effect;
		return this;
	}

	public ButtomDialog builder() {
		final View view = LayoutInflater.from(context).inflate(
				R.layout.bottom_dialog, null);

		view.setMinimumWidth(display.getWidth());

		sLayout_content = (ScrollView) view.findViewById(R.id.sLayout_content);
		lLayout_content = (LinearLayout) view
				.findViewById(R.id.lLayout_content);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
		txt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog = new Dialog(context, R.style.MyDialogStyle);
		dialog.setContentView(view);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.x = 0;
		lp.y = 0;
		dialogWindow.setAttributes(lp);
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				if(null != effectstype){
					BaseEffects animator = effectstype.getAnimator();
					animator.setDuration(300);
					animator.start(view);
				}
			}
		});
		return this;
	}

	public ButtomDialog setTitle(String title) {
		showTitle = true;
		txt_title.setVisibility(View.VISIBLE);
		txt_title.setText(title);
		return this;
	}

	public ButtomDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public ButtomDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	public ButtomDialog addSheetItem(SpannableString strItem, SheetItemColor color,
									 OnSheetItemClickListener listener) {
		if (sheetItemList == null) {
			sheetItemList = new ArrayList<SheetItem>();
		}
		sheetItemList.add(new SheetItem(strItem, color, listener));
		return this;
	}

	private void setSheetItems() {
		if (sheetItemList == null || sheetItemList.size() <= 0) {
			return;
		}

		int size = sheetItemList.size();

		if (size >= 7) {
			LayoutParams params = (LayoutParams) sLayout_content
					.getLayoutParams();
			params.height = display.getHeight() / 2;
			sLayout_content.setLayoutParams(params);
		}

		for (int i = 1; i <= size; i++) {
			final int index = i;
			SheetItem sheetItem = sheetItemList.get(i - 1);
			SpannableString strItem = sheetItem.name;
			SheetItemColor color = sheetItem.color;
			final OnSheetItemClickListener listener = (OnSheetItemClickListener) sheetItem.itemClickListener;

			TextView textView = new TextView(context);
			textView.setHeight(88);
			textView.setText(strItem);
			textView.setTextSize(16);
			textView.setGravity(Gravity.CENTER);

			if (size == 1) {
				if (showTitle) {
					textView.setBackgroundResource(R.drawable.dialog_down);
				} else {
					textView.setBackgroundResource(R.drawable.dialog_singel);
				}
			} else {
				if (showTitle) {
					if (i >= 1 && i < size) {
						textView.setBackgroundResource(R.drawable.dialog_mid);
					} else {
						textView.setBackgroundResource(R.drawable.dialog_down);
					}
				} else {
					if (i == 1) {
						textView.setBackgroundResource(R.drawable.dialog_up);
					} else if (i < size) {
						textView.setBackgroundResource(R.drawable.dialog_mid);
					} else {
						textView.setBackgroundResource(R.drawable.dialog_down);
					}
				}
			}

			if (color == null) {
				textView.setTextColor(Color.parseColor(SheetItemColor.Blue
						.getName()));
			} else {
				textView.setTextColor(Color.parseColor(color.getName()));
			}

			float scale = context.getResources().getDisplayMetrics().density;
			int height = (int) (45 * scale + 0.5f);
			textView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, height));

			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onClick(index);
					dialog.dismiss();
				}
			});

			lLayout_content.addView(textView);
		}
	}

	public void show() {
		setSheetItems();
		dialog.show();
	}

	public interface OnSheetItemClickListener {
		void onClick(int which);
	}

	public class SheetItem {
		SpannableString name;
		OnSheetItemClickListener itemClickListener;
		SheetItemColor color;

		public SheetItem(SpannableString name, SheetItemColor color,
						 OnSheetItemClickListener itemClickListener) {
			this.name = name;
			this.color = color;
			this.itemClickListener = itemClickListener;
		}
	}

	public enum SheetItemColor {
		Blue("#037BFF"), Red("#FD4A2E"),Black("#333333");

		private String name;

		private SheetItemColor(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
