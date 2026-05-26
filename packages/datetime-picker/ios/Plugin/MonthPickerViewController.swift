import UIKit

@objc public class MonthPicker: NSObject {

    private static let sharedInstance = MonthPicker()
    private var isPresented = false
    private var activeController: MonthPickerViewController?

    @objc class func selectMonth(cancelText: String? = nil,
                                 doneText: String = "Done",
                                 selectedDate: Date = Date(),
                                 minDate: Date? = nil,
                                 maxDate: Date? = nil,
                                 locale: Locale? = nil,
                                 theme: Theme = .auto,
                                 completion: ((_ date: Date?, _ errorCode: ErrorCode) -> Void)?) {

        guard let parent = UIApplication.topViewController() else { return }
        guard MonthPicker.sharedInstance.isPresented == false else { return }

        MonthPicker.sharedInstance.isPresented = true

        let vc = MonthPickerViewController(cancelText: cancelText, doneText: doneText, selectedDate: selectedDate,
                                           minDate: minDate, maxDate: maxDate, locale: locale, theme: theme)
        vc.modalPresentationStyle = .overCurrentContext
        vc.modalTransitionStyle = .crossDissolve

        vc.onMonthSelected = { (selectedData) in
            completion?(selectedData, ErrorCode.none)
        }
        vc.onCanceled = {
            completion?(nil, ErrorCode.canceled)
        }
        vc.onBackdropDismissed = {
            completion?(nil, ErrorCode.dismissed)
        }
        vc.onWillDismiss = {
            MonthPicker.sharedInstance.isPresented = false
            MonthPicker.sharedInstance.activeController = nil
        }

        MonthPicker.sharedInstance.activeController = vc
        parent.present(vc, animated: true, completion: nil)
    }

    @objc class func cancel() {
        DispatchQueue.main.async {
            if let controller = MonthPicker.sharedInstance.activeController {
                controller.dismiss(animated: true, completion: nil)
                MonthPicker.sharedInstance.activeController = nil
                MonthPicker.sharedInstance.isPresented = false
            }
        }
    }
}

class MonthPickerViewController: UIViewController, UIPickerViewDataSource, UIPickerViewDelegate {

    // MARK: - Public closures
    var onMonthSelected: ((_ date: Date) -> Void)?
    var onCanceled: (() -> Void)?
    var onBackdropDismissed: (() -> Void)?
    var onWillDismiss: (() -> Void)?

    // MARK: - Configuration
    private let selectedDate: Date
    private let minDate: Date?
    private let maxDate: Date?
    private let cancelText: String?
    private let doneText: String
    private let locale: Locale
    private let theme: Theme

    // MARK: - Picker state
    private var monthSymbols: [String] = []
    private var years: [Int] = []
    private var selectedMonthIndex: Int = 0
    private var selectedYearIndex: Int = 0

    // MARK: - Layout constants
    private let barViewHeight: CGFloat = 44
    private let pickerHeight: CGFloat = 216
    private let buttonWidth: CGFloat = 84
    private let lineHeight: CGFloat = 0.5
    private let buttonColor = UIColor(red: 72/255, green: 152/255, blue: 240/255, alpha: 1)
    private let lineColor = UIColor(red: 240/255, green: 240/255, blue: 240/255, alpha: 1)

    // MARK: - Init
    init(cancelText: String? = nil,
         doneText: String = "Done",
         selectedDate: Date = Date(),
         minDate: Date? = nil,
         maxDate: Date? = nil,
         locale: Locale? = nil,
         theme: Theme = .auto) {

        self.cancelText = cancelText
        self.doneText = doneText
        self.selectedDate = selectedDate
        self.minDate = minDate
        self.maxDate = maxDate
        self.locale = locale ?? Locale.current
        self.theme = theme

        super.init(nibName: nil, bundle: nil)

        configurePickerData()
        initialSetup()
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func willTransition(to newCollection: UITraitCollection, with coordinator: UIViewControllerTransitionCoordinator) {
        if newCollection.userInterfaceStyle != traitCollection.userInterfaceStyle {
            setUpThemeMode(theme: theme, systemIsDark: newCollection.userInterfaceStyle == .dark)
        }
    }

    // MARK: - Setup
    private func configurePickerData() {
        var calendar = Calendar(identifier: .gregorian)
        calendar.locale = locale

        let formatter = DateFormatter()
        formatter.locale = locale
        monthSymbols = formatter.standaloneMonthSymbols ?? formatter.monthSymbols ?? (1...12).map { String($0) }

        let currentYear = calendar.component(.year, from: Date())
        var minYear = currentYear - 100
        var maxYear = currentYear + 100
        if let minDate = minDate {
            minYear = calendar.component(.year, from: minDate)
        }
        if let maxDate = maxDate {
            maxYear = calendar.component(.year, from: maxDate)
        }
        if minYear > maxYear {
            minYear = maxYear
        }
        years = Array(minYear...maxYear)

        let initialYear = clamp(calendar.component(.year, from: selectedDate), min: minYear, max: maxYear)
        let initialMonth = calendar.component(.month, from: selectedDate) - 1

        selectedYearIndex = years.firstIndex(of: initialYear) ?? 0
        selectedMonthIndex = clamp(initialMonth, min: 0, max: 11)
    }

    private func initialSetup() {
        view.backgroundColor = UIColor.clear
        view.addSubview(transView)
        transView.surroundConstraints(view)

        stackView.addArrangedSubview(topLine)
        stackView.addArrangedSubview(toolBarView)
        stackView.addArrangedSubview(bottomLine)
        stackView.addArrangedSubview(pickerView)

        view.addSubview(stackView)

        let height = barViewHeight + (2 * lineHeight) + pickerHeight
        stackView.pinConstraints(view, left: 0, right: 0, bottom: 0, height: height)

        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            self.pickerView.selectRow(self.selectedMonthIndex, inComponent: 0, animated: false)
            self.pickerView.selectRow(self.selectedYearIndex, inComponent: 1, animated: false)
        }

        setUpThemeMode(theme: theme, systemIsDark: traitCollection.userInterfaceStyle == .dark)
    }

    private func setUpThemeMode(theme: Theme, systemIsDark: Bool) {
        switch theme {
        case .light:
            applyLightTheme()
        case .dark:
            applyDarkTheme()
        case .auto:
            if systemIsDark { applyDarkTheme() } else { applyLightTheme() }
        }
    }

    private func applyLightTheme() {
        stackView.backgroundColor = UIColor.white
        transView.backgroundColor = UIColor(white: 0.1, alpha: 0.3)
        pickerView.backgroundColor = UIColor.white
        pickerView.overrideUserInterfaceStyle = .light
        toolBarView.backgroundColor = UIColor.white
        pickerView.reloadAllComponents()
    }

    private func applyDarkTheme() {
        stackView.backgroundColor = UIColor.black
        transView.backgroundColor = UIColor(white: 1, alpha: 0.3)
        pickerView.backgroundColor = UIColor.black
        pickerView.overrideUserInterfaceStyle = .dark
        toolBarView.backgroundColor = UIColor.black
        pickerView.reloadAllComponents()
    }

    // MARK: - Actions
    private func dismissVC() {
        onWillDismiss?()
        dismiss(animated: true, completion: nil)
    }

    @objc func handleTap() {
        onBackdropDismissed?()
        dismissVC()
    }

    @objc func onDoneButton(sender: UIButton) {
        let year = years[selectedYearIndex]
        let month = selectedMonthIndex + 1

        var calendar = Calendar(identifier: .gregorian)
        calendar.locale = locale
        var components = DateComponents()
        components.year = year
        components.month = month
        components.day = 1
        components.hour = 0
        components.minute = 0
        components.second = 0
        let date = calendar.date(from: components) ?? Date()

        onMonthSelected?(date)
        dismissVC()
    }

    @objc func onCancelButton(sender: UIButton) {
        onCanceled?()
        dismissVC()
    }

    private func clamp(_ value: Int, min: Int, max: Int) -> Int {
        return Swift.max(min, Swift.min(max, value))
    }

    // MARK: - UIPickerViewDataSource
    func numberOfComponents(in pickerView: UIPickerView) -> Int { return 2 }

    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return component == 0 ? monthSymbols.count : years.count
    }

    func pickerView(_ pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
        let title = component == 0 ? monthSymbols[row] : String(years[row])
        return NSAttributedString(string: title, attributes: [.foregroundColor: currentTextColor()])
    }

    private func currentTextColor() -> UIColor {
        switch theme {
        case .light:
            return UIColor.black
        case .dark:
            return UIColor.white
        case .auto:
            return traitCollection.userInterfaceStyle == .dark ? UIColor.white : UIColor.black
        }
    }

    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if component == 0 {
            selectedMonthIndex = row
        } else {
            selectedYearIndex = row
        }
        enforceMinMaxConstraints(pickerView: pickerView)
    }

    private func enforceMinMaxConstraints(pickerView: UIPickerView) {
        var calendar = Calendar(identifier: .gregorian)
        calendar.locale = locale

        let year = years[selectedYearIndex]
        let month = selectedMonthIndex + 1

        if let minDate = minDate {
            let minYear = calendar.component(.year, from: minDate)
            let minMonth = calendar.component(.month, from: minDate)
            if year == minYear && month < minMonth {
                selectedMonthIndex = minMonth - 1
                pickerView.selectRow(selectedMonthIndex, inComponent: 0, animated: true)
            }
        }
        if let maxDate = maxDate {
            let maxYear = calendar.component(.year, from: maxDate)
            let maxMonth = calendar.component(.month, from: maxDate)
            if year == maxYear && month > maxMonth {
                selectedMonthIndex = maxMonth - 1
                pickerView.selectRow(selectedMonthIndex, inComponent: 0, animated: true)
            }
        }
    }

    // MARK: - Views
    private lazy var transView: UIView = {
        let vw = UIView()
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.handleTap))
        vw.addGestureRecognizer(tapGesture)
        vw.isUserInteractionEnabled = true
        return vw
    }()

    private lazy var stackView: UIStackView = {
        let sv = UIStackView()
        sv.axis = .vertical
        sv.distribution = .fill
        sv.alignment = .center
        sv.spacing = 0
        return sv
    }()

    private lazy var pickerView: UIPickerView = {
        let pv = UIPickerView()
        pv.dataSource = self
        pv.delegate = self
        pv.pinConstraints(view, height: pickerHeight, width: view.frame.width)
        return pv
    }()

    private lazy var toolBarView: UIView = {
        let barView = UIView()
        barView.pinConstraints(view, height: barViewHeight, width: view.frame.width)

        let doneButton = self.doneButton
        let cancelButton = self.cancelButton

        barView.addSubview(doneButton)
        barView.addSubview(cancelButton)

        cancelButton.pinConstraints(barView, left: 0, top: 0, bottom: 0, width: buttonWidth)
        doneButton.pinConstraints(barView, right: 0, top: 0, bottom: 0, width: buttonWidth)

        doneButton.setTitle(doneText, for: .normal)
        if let text = cancelText {
            cancelButton.setTitle(text, for: .normal)
        } else {
            cancelButton.isHidden = true
        }

        return barView
    }()

    private lazy var topLine: UILabel = lineLabel()
    private lazy var bottomLine: UILabel = lineLabel()

    private func lineLabel() -> UILabel {
        let label = UILabel()
        label.backgroundColor = lineColor
        label.pinConstraints(view, height: lineHeight, width: view.frame.width)
        return label
    }

    private lazy var doneButton: UIButton = {
        let button = UIButton()
        button.setTitleColor(buttonColor, for: .normal)
        button.titleLabel?.font = UIFont(name: "HelveticaNeue-Bold", size: 16)
        button.addTarget(self, action: #selector(onDoneButton), for: .touchUpInside)
        return button
    }()

    private lazy var cancelButton: UIButton = {
        let button = UIButton()
        button.setTitleColor(buttonColor, for: .normal)
        button.titleLabel?.font = UIFont(name: "HelveticaNeue-Bold", size: 16)
        button.addTarget(self, action: #selector(onCancelButton), for: .touchUpInside)
        return button
    }()
}

// MARK: - Private helpers

private extension UIView {
    func pinConstraints(_ byView: UIView, left: CGFloat? = nil, right: CGFloat? = nil, top: CGFloat? = nil, bottom: CGFloat? = nil, height: CGFloat? = nil, width: CGFloat? = nil) {
        translatesAutoresizingMaskIntoConstraints = false
        if let left = left { leftAnchor.constraint(equalTo: byView.leftAnchor, constant: left).isActive = true }
        if let right = right { rightAnchor.constraint(equalTo: byView.rightAnchor, constant: right).isActive = true }
        if let top = top { topAnchor.constraint(equalTo: byView.topAnchor, constant: top).isActive = true }
        if let bottom = bottom { bottomAnchor.constraint(equalTo: byView.bottomAnchor, constant: bottom).isActive = true }
        if let height = height { heightAnchor.constraint(equalToConstant: height).isActive = true }
        if let width = width { widthAnchor.constraint(equalToConstant: width).isActive = true }
    }

    func surroundConstraints(_ byView: UIView, left: CGFloat = 0, right: CGFloat = 0, top: CGFloat = 0, bottom: CGFloat = 0) {
        pinConstraints(byView, left: left, right: right, top: top, bottom: bottom)
    }
}

private extension UIApplication {
    static var keyWindow: UIWindow? {
        if #available(iOS 13.0, *) {
            return UIApplication.shared.windows.filter { $0.isKeyWindow }.first
        } else {
            return UIApplication.shared.delegate?.window ?? nil
        }
    }

    static func topViewController(controller: UIViewController? = UIApplication.keyWindow?.rootViewController) -> UIViewController? {
        if let nc = controller as? UINavigationController {
            return topViewController(controller: nc.viewControllers.last ?? nc)
        }
        if let tabController = controller as? UITabBarController {
            if let selected = tabController.selectedViewController {
                return topViewController(controller: selected)
            }
        }
        if let presented = controller?.presentedViewController {
            return topViewController(controller: presented)
        }
        return controller
    }
}
